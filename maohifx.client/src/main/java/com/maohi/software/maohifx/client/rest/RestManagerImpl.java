/**
 *
 */
package com.maohi.software.maohifx.client.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import javafx.fxml.FXMLLoader;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.runtime.ScriptObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class RestManagerImpl {

	private final FXMLLoader loader;

	public RestManagerImpl(final FXMLLoader aLoader) {
		this.loader = aLoader;
	}

	public void ajax(final JSObject aJsObject) throws ScriptException, NoSuchMethodException {
		final Client iClient = ClientBuilder.newClient();
		final WebTarget iTarget = iClient.target((String) aJsObject.getMember("url"));
		final Builder iBuilder = iTarget.request();

		final Response iResponse;
		switch ((String) aJsObject.getMember("type")) {
		case "get":
			iResponse = iBuilder.get();
			break;

		case "post":
			iResponse = iBuilder.post(Entity.json((JSObject) aJsObject.getMember(("data"))));
			break;

		default:
			return;
		}

		final int iStatus = iResponse.getStatus();

		switch (Status.fromStatusCode(iStatus)) {
		case OK:
			switch (iResponse.getHeaderString("Content-Type")) {
			case MediaType.APPLICATION_JSON:
				this.executeFunction("success", aJsObject, iStatus, iResponse.readEntity(Object.class));
				break;

			case "application/pdf":
				final File iFile = this.generatePDFFile(iResponse.readEntity(InputStream.class));
				this.executeFunction("success", aJsObject, iStatus, iFile);
				break;

			default:
				break;
			}
			break;

		default:
			this.executeFunction("error", aJsObject, iStatus, iResponse.readEntity(String.class));
			break;
		}
	}

	private void executeFunction(final String aMethodName, final JSObject aJsObject, final int iStatus, final Object iEntity) throws ScriptException, NoSuchMethodException {
		String iMethod = aJsObject.getMember(aMethodName).toString();
		if ((iMethod != null) && !iMethod.equals("undefined")) {
			iMethod = iMethod.replace("function", "function " + aMethodName);

			final ScriptEngineManager iEngineManager = new ScriptEngineManager();
			final ScriptEngine iEngine = iEngineManager.getEngineByName("nashorn");
			for (final String iKey : this.loader.getNamespace().keySet()) {
				iEngine.put(iKey, this.loader.getNamespace().get(iKey));
			}
			iEngine.eval(iMethod);
			((Invocable) iEngine).invokeFunction(aMethodName, iEntity, iStatus);
		}
	}

	private File generatePDFFile(final InputStream aInputStream) {
		File iFile = null;
		try {
			iFile = File.createTempFile("maohifx", ".pdf");
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (OutputStream iOutputStream = new FileOutputStream(iFile)) {

			int iRead = 0;
			final byte[] iBytes = new byte[1024];

			while ((iRead = aInputStream.read(iBytes)) != -1) {
				iOutputStream.write(iBytes, 0, iRead);
			}

			iFile.createNewFile();

			return iFile;

		} catch (final FileNotFoundException aException) {
			// TODO Auto-generated catch block
			aException.printStackTrace();

			return null;
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public void get(final JSObject aJsObject) throws NoSuchMethodException, ScriptException {
		aJsObject.setMember("type", "get");
		this.ajax(aJsObject);
	}

	public void get(final String aUrl) throws NoSuchMethodException, ScriptException {
		this.ajax(this.newJSObject(aUrl, "get"));
	}

	private JSObject newJSObject(final String aUrl, final String aType) {
		final JSObject iJSObject = ScriptUtils.wrap(new ScriptObject() {
		});
		iJSObject.setMember("url", aUrl);
		iJSObject.setMember("type", aType);
		return iJSObject;
	}

	public void post(final JSObject aJsObject) throws NoSuchMethodException, ScriptException {
		aJsObject.setMember("type", "post");
		this.ajax(aJsObject);
	}

	public void post(final String aUrl) throws NoSuchMethodException, ScriptException {
		this.ajax(this.newJSObject(aUrl, "post"));
	}

}
