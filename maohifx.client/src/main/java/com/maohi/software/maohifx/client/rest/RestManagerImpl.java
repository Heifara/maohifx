/**
 * 
 */
package com.maohi.software.maohifx.client.rest;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.maohi.software.maohifx.client.ExtFXMLLoader;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.runtime.ScriptObject;

/**
 * @author heifara
 *
 */
public class RestManagerImpl {

	private final ExtFXMLLoader loader;

	public RestManagerImpl(final ExtFXMLLoader aLoader) {
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

		default:
			return;
		}

		final int iStatus = iResponse.getStatus();

		switch (Status.fromStatusCode(iStatus)) {
		case OK:
			final Object iEntity = iResponse.readEntity(Object.class);
			this.executeFunction("success", aJsObject, iStatus, iEntity);
			break;

		default:
			this.executeFunction("error", aJsObject, iStatus, null);
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
