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

import com.maohi.software.maohifx.client.event.AuthentificationEvent;
import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.event.SuccesEvent;

import javafx.event.Event;
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

	private final URL location;
	private EventHandler<Event> onStart;
	private EventHandler<AuthentificationEvent> onAuthentification;
	private EventHandler<Event> onEnd;

	public HttpHandler(final URL aLocation) {
		this.location = aLocation;
	}

	public void ajax(final JSObject aJsObject) {
		final URLHandler iURLHandler = new URLHandler();
		iURLHandler.setOnStart(this.onStart);
		iURLHandler.setOnAuthentification(this.onAuthentification);
		iURLHandler.setOnEnd(this.onEnd);
		iURLHandler.setOnSucces(new EventHandler<SuccesEvent>() {

			@Override
			public void handle(final SuccesEvent aEvent) {
				final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aJsObject;
				final ScriptObjectMirror iSuccessMethod = (ScriptObjectMirror) iScriptObject.get("success");
				iSuccessMethod.call(iScriptObject, aEvent.getItem(), Status.OK);
			}
		});
		iURLHandler.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

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
					iURLHandler.process(HttpHandler.this.location, aJsObject);
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

	/**
	 * @param onAuthentification
	 *            the onAuthentification to set
	 */
	public void setOnAuthentification(final EventHandler<AuthentificationEvent> onAuthentification) {
		this.onAuthentification = onAuthentification;
	}

	/**
	 * @param onEnd
	 *            the onEnd to set
	 */
	public void setOnEnd(final EventHandler<Event> onEnd) {
		this.onEnd = onEnd;
	}

	/**
	 * @param onStart
	 *            the onStart to set
	 */
	public void setOnStart(final EventHandler<Event> onStart) {
		this.onStart = onStart;
	}
}
