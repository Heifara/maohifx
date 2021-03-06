/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.maohi.software.maohifx.client.event.AuthentificationEvent;
import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.event.SuccesEvent;
import com.maohi.software.maohifx.common.Files;
import com.sun.media.jfxmedia.MediaException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class URLHandler {

	private EventHandler<Event> onStart;
	private EventHandler<Event> onEnd;
	private EventHandler<SuccesEvent> onSucces;
	private EventHandler<ExceptionEvent> onExceptionThrown;
	private EventHandler<AuthentificationEvent> onAuthentification;

	public URLHandler() {
		this.onStart = new EventHandler<Event>() {

			@Override
			public void handle(final Event aEvent) {
			}
		};
		this.onExceptionThrown = new EventHandler<ExceptionEvent>() {

			@Override
			public void handle(final ExceptionEvent aEvent) {
				aEvent.getException().printStackTrace();
			}
		};
		this.onEnd = new EventHandler<Event>() {

			@Override
			public void handle(final Event aEvent) {
			}
		};
		this.onAuthentification = new EventHandler<AuthentificationEvent>() {

			@Override
			public void handle(final AuthentificationEvent event) {
			}
		};
	}

	private void checkInterrupted() throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException("Thread interrupted by user " + Thread.currentThread().getName());
		}
	}

	private Entity<?> getEntity(final String aDataType, final Object aData) {
		switch (aDataType) {
		case MediaType.APPLICATION_JSON:
			return Entity.json(aData);

		case MediaType.APPLICATION_XML:
			return Entity.xml(aData);

		default:
			throw new IllegalArgumentException(aDataType + " not handle");
		}
	}

	public void process(final URL aLocation, final JSObject aJsObject) throws MalformedURLException, IOException, InterruptedException {
		final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aJsObject;

		if (!iScriptObject.hasMember("type")) {
			throw new JsonMappingException("Type is undefined");
		}

		String iUrl = (String) iScriptObject.getMember("url");
		if (iUrl.startsWith("@/")) {
			iUrl = iUrl.replace("@/", "@");
		}
		if (iUrl.startsWith("@")) {
			final StringBuilder iBaseUrl = new StringBuilder();
			iBaseUrl.append(aLocation.getProtocol());
			iBaseUrl.append("://");
			iBaseUrl.append(aLocation.getHost());
			iBaseUrl.append(":");
			iBaseUrl.append(aLocation.getPort());
			iBaseUrl.append("/");

			iUrl = iUrl.replace("@", iBaseUrl.toString());
		}

		final String iRequestType = (String) iScriptObject.getMember("type");

		Entity<?> iEntity = null;
		if (iScriptObject.hasMember("dataType") && iScriptObject.hasMember("data")) {
			iEntity = this.getEntity((String) iScriptObject.getMember("dataType"), iScriptObject.getMember(("data")));
		}

		this.process(new URL(iUrl), iRequestType, iEntity);
	}

	public void process(final URL aUrl, final String aRequestType, final Entity<?> aEntity) throws IOException, InterruptedException {
		this.onStart.handle(new Event(Event.ANY));

		final URL iUrl = aUrl;
		final Response iResponse = this.processHttp(iUrl, aRequestType, aEntity);
		if (iResponse != null) {
			this.processResponse(iResponse, aUrl);
		}

		this.onEnd.handle(new Event(Event.ANY));
	}

	protected Response processHttp(final URL iUrl, final String aRequestType, final Entity<?> aEntity) throws InterruptedException {
		try {
			// Initialize Jersey client
			final WebTarget iTarget = ClientBuilder.newClient().target(iUrl.toURI());
			this.onAuthentification.handle(new AuthentificationEvent(Event.ANY, this, iTarget));
			this.checkInterrupted();

			// Handle QueryParam
			if ((iUrl.getQuery() != null) && !iUrl.getQuery().isEmpty()) {
				final String[] iQueryParams = iUrl.getQuery().split("&");
				for (final String iQueryParam : iQueryParams) {
					final String[] iKeyValue = iQueryParam.split("=");
					String iKey = "";
					String iValue = "";

					if (iKeyValue.length > 0) {
						iKey = iKeyValue[0];
					}
					if (iKeyValue.length > 1) {
						iValue = iQueryParam.split("=")[1];
					}
					if (!iKey.isEmpty()) {
						iTarget.queryParam(iKey, iValue);
					}
				}
			}
			this.checkInterrupted();

			// Request the server
			Response iResponse = null;
			switch (aRequestType) {
			case "get":
				iResponse = iTarget.request().get();
				break;

			case "post":
				if (aEntity == null) {
					throw new IllegalArgumentException("Can not post with null Entity");
				}
				iResponse = iTarget.request().post(aEntity);
				break;

			default:
				throw new IllegalArgumentException();
			}
			this.checkInterrupted();
			return iResponse;

		} catch (final InterruptedException aException) {
			throw aException;

		} catch (final Exception aException) {
			this.checkInterrupted();

			this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
			return null;
		}
	}

	private void processResponse(final Response iResponse, final URL aUrl) throws IOException, InterruptedException {
		try {
			switch (Status.fromStatusCode(iResponse.getStatus())) {
			case OK:
				this.checkInterrupted();
				this.processResponseOk(iResponse, aUrl);
				break;

			case FORBIDDEN:
				throw new ForbiddenException(iResponse);

			case UNAUTHORIZED:
				throw new NotAuthorizedException(iResponse);

			case METHOD_NOT_ALLOWED:
				throw new NotAuthorizedException(iResponse);

			case INTERNAL_SERVER_ERROR:
				throw new InternalServerErrorException(iResponse);

			case NOT_FOUND:
				throw new NotFoundException(iResponse);

			case NOT_ACCEPTABLE:
				throw new NotAcceptableException(iResponse);

			default:
				throw new NotSupportedException(iResponse);
			}
		} catch (final InterruptedException aException) {
			throw aException;

		} catch (final Exception aException) {
			this.checkInterrupted();

			this.onExceptionThrown.handle(new ExceptionEvent(this, aException, iResponse.getStatus()));
		}
	}

	private void processResponseOk(final Response iResponse, final URL aUrl) throws MalformedURLException, IOException, InterruptedException {
		final String iContentType = iResponse.getHeaderString("Content-Type");
		if (iContentType != null) {
			this.checkInterrupted();
			if (iContentType.contains(MediaType.TEXT_HTML)) {
				this.onSucces.handle(new SuccesEvent(this, Files.createTmpFile(iResponse.readEntity(InputStream.class), "", ".html"), aUrl));
			} else if (iContentType.contains("application/pdf")) {
				this.onSucces.handle(new SuccesEvent(this, Files.createTmpFile(iResponse.readEntity(InputStream.class), "", ".pdf"), aUrl));
			} else if (iContentType.contains("image")) {
				this.onSucces.handle(new SuccesEvent(this, new Image(iResponse.readEntity(InputStream.class)), aUrl));
			} else if (iContentType.contains(MediaType.APPLICATION_JSON)) {
				this.onSucces.handle(new SuccesEvent(this, iResponse, aUrl));
			} else if (iContentType.contains(MediaType.TEXT_PLAIN)) {
				this.onSucces.handle(new SuccesEvent(this, Files.createTmpFile(iResponse.readEntity(InputStream.class), "", ".txt"), aUrl));
			} else if (iContentType.contains(MediaType.APPLICATION_XML)) {
				this.onSucces.handle(new SuccesEvent(this, Files.createTmpFile(iResponse.readEntity(InputStream.class), "", ".xml"), aUrl));
			} else {
				throw new MediaException(iResponse.readEntity(String.class));
			}
		} else {
			this.onSucces.handle(new SuccesEvent(this, aUrl));
		}
	}

	public void setOnAuthentification(final EventHandler<AuthentificationEvent> onAuthentification) {
		this.onAuthentification = onAuthentification;
	}

	public void setOnEnd(final EventHandler<Event> aOnEnd) {
		this.onEnd = aOnEnd;
	}

	public void setOnExceptionThrown(final EventHandler<ExceptionEvent> onExceptionThrown) {
		this.onExceptionThrown = onExceptionThrown;
	}

	public void setOnStart(final EventHandler<Event> onStart) {
		this.onStart = onStart;
	}

	public void setOnSucces(final EventHandler<SuccesEvent> aOnSucces) {
		this.onSucces = aOnSucces;
	}

	public URL toHttp(final URL iUrl) throws MalformedURLException {
		final StringBuilder iURLBuilder = new StringBuilder();
		iURLBuilder.append("http://");
		iURLBuilder.append(iUrl.getHost());
		iURLBuilder.append(iUrl.getPort() != 0 ? ":" + iUrl.getPort() : "");
		iURLBuilder.append(iUrl.getPath());

		if (!iUrl.getPath().endsWith(".html") && !iUrl.getPath().endsWith(".fxml")) {
			if (!iUrl.getPath().endsWith("/")) {
				iURLBuilder.append("/");
			}
			if (!iUrl.getPath().endsWith(".fxml")) {
				iURLBuilder.append("index.fxml");
			}
		}
		return new URL(iURLBuilder.toString().replace("webapi/", ""));
	}

}
