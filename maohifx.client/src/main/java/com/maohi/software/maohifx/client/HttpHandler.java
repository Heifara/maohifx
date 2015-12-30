/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.script.ScriptException;
import javax.ws.rs.core.Response.Status;

import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.event.SuccesEvent;

import javafx.event.EventHandler;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.runtime.ScriptObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class HttpHandler {

	private final URLHandler urlHandler;
	private final URL location;

	public HttpHandler(final URLHandler urlHandler, final URL aLocation) {
		this.urlHandler = urlHandler;

		this.location = aLocation;
	}

	public void ajax(final JSObject aJsObject) {
		this.urlHandler.setOnSucces(new EventHandler<SuccesEvent>() {

			@Override
			public void handle(final SuccesEvent aEvent) {
				final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aJsObject;
				final ScriptObjectMirror iSuccessMethod = (ScriptObjectMirror) iScriptObject.get("success");
				iSuccessMethod.call(iScriptObject, aEvent.getItem(), Status.OK);
			}
		});
		this.urlHandler.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

			@Override
			public void handle(final ExceptionEvent aEvent) {
				final StringWriter iStringWriter = new StringWriter();
				final PrintWriter iPrintWriter = new PrintWriter(iStringWriter);
				aEvent.getException().printStackTrace(iPrintWriter);

				final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aJsObject;
				final ScriptObjectMirror iSuccessMethod = (ScriptObjectMirror) iScriptObject.get("error");
				iSuccessMethod.call(iScriptObject, aEvent.getException().getMessage(), aEvent.getStatusCode(), iStringWriter.toString());
			}
		});
		final Thread iThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HttpHandler.this.urlHandler.process(HttpHandler.this.location, aJsObject);
				} catch (final IOException aException) {
					aException.printStackTrace();
				}
			}
		});
		iThread.start();
	}

	public void get(final JSObject aJsObject) throws NoSuchMethodException, ScriptException, IOException {
		aJsObject.setMember("type", "get");
		this.ajax(aJsObject);
	}

	public void get(final String aUrl) throws NoSuchMethodException, ScriptException, IOException {
		this.ajax(this.newJSObject(aUrl, "get"));
	}

	private JSObject newJSObject(final String aUrl, final String aType) {
		final JSObject iJSObject = ScriptUtils.wrap(new ScriptObject() {
		});
		iJSObject.setMember("url", aUrl);
		iJSObject.setMember("type", aType);
		return iJSObject;
	}

	public void post(final JSObject aJsObject) throws NoSuchMethodException, ScriptException, IOException {
		aJsObject.setMember("type", "post");
		this.ajax(aJsObject);
	}

	public void post(final String aUrl) throws NoSuchMethodException, ScriptException, IOException {
		this.ajax(this.newJSObject(aUrl, "post"));
	}
}
