/**
 *
 */
package com.maohi.software.maohifx.client.event;

import java.net.URL;

import javax.ws.rs.core.Response;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author heifara
 *
 */
public class SuccesEvent extends Event {

	private static final long serialVersionUID = 1L;
	public static final EventType<ExceptionEvent> SUCCES = new EventType<>("SUCCES");
	private final Object item;
	private final URL url;
	private final boolean fxml;
	private final String contentType;

	public SuccesEvent(final Object aSource, final Object aItem, final URL aUrl) {
		super(SUCCES);

		this.source = aSource;
		this.item = aItem;
		this.url = aUrl;
		this.fxml = true;
		this.contentType = "";
	}

	public SuccesEvent(final Object aSource, final Response aResponse, final URL aUrl) {
		super(SUCCES);

		this.source = aSource;

		this.item = aResponse.readEntity(Object.class);
		this.url = aUrl;

		this.contentType = aResponse.getHeaderString("Content-Type");
		if (aResponse.getHeaderString("fxml") == null) {
			this.fxml = true;
		} else {
			this.fxml = Boolean.parseBoolean(aResponse.getHeaderString("fxml"));
		}
	}

	public SuccesEvent(final Object aSource, final URL aUrl) {
		super(SUCCES);

		this.source = aSource;
		this.item = null;
		this.url = aUrl;
		this.fxml = true;
		this.contentType = "";

	}

	public String getContentType() {
		return this.contentType;
	}

	public Object getItem() {
		return this.item;
	}

	public URL getUrl() {
		return this.url;
	}

	public boolean isFxml() {
		return this.fxml;
	}

}
