/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
public class HttpHandler implements Runnable {

	public class JSURLHandler {
		private final URLHandler urlHandler;
		private final JSObject jsObject;

		public JSURLHandler(final URLHandler aUrlHandler, final JSObject aJsObject) {
			super();
			this.urlHandler = aUrlHandler;
			this.jsObject = aJsObject;
		}

	}

	private final URL location;
	private EventHandler<Event> onStart;
	private EventHandler<AuthentificationEvent> onAuthentification;
	private EventHandler<Event> onEnd;

	private final List<JSURLHandler> pendingUrlHandlers;

	public HttpHandler(final URL aLocation) {
		this.location = aLocation;

		this.pendingUrlHandlers = new ArrayList<>();
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

		this.runLater(new JSURLHandler(iURLHandler, aJsObject));
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

	@Override
	public synchronized void run() {
		try {
			if (!this.pendingUrlHandlers.isEmpty()) {
				final List<JSURLHandler> iRunningURLHandlers = new ArrayList<>();
				iRunningURLHandlers.addAll(this.pendingUrlHandlers);
				for (final JSURLHandler iJSURLHandler : iRunningURLHandlers) {
					iJSURLHandler.urlHandler.process(HttpHandler.this.location, iJSURLHandler.jsObject);
				}
				this.pendingUrlHandlers.removeAll(iRunningURLHandlers);
			}
		} catch (final InterruptedException aException) {
			System.err.println(aException.getMessage());
		} catch (final IOException aException) {
			aException.printStackTrace();
		}
	}

	private void runLater(final JSURLHandler aJsurlHandler) {
		this.pendingUrlHandlers.add(aJsurlHandler);

		final Thread iThread = new Thread(this);
		iThread.setName(this.getClass().getSimpleName());
		iThread.start();
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
