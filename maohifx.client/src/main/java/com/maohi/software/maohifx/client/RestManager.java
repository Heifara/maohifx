/**
 * 
 */
package com.maohi.software.maohifx.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jdk.nashorn.api.scripting.JSObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class RestManager {

	public Response get(JSObject aJSObject) {
		return this.get((String) aJSObject.getMember("url"));
	}

	public Response get(String aUrl) {
		return ClientBuilder.newClient().target(aUrl).request().get();
	}

	public Response post(JSObject aJSObject) {
		return this.post((String) aJSObject.getMember("url"));
	}

	public Response post(String aUrl) {
		return ClientBuilder.newClient().target(aUrl).request().post(Entity.entity(new String("Hi"), MediaType.APPLICATION_FORM_URLENCODED));
	}

}
