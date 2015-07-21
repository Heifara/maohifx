/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.maohi.software.samples.beans.Person;

/**
 * @author heifara
 *
 */
@Path("post")
@Produces({ MediaType.APPLICATION_JSON })
public class Post {

	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response fromForm(@FormParam("login") String aLogin, @FormParam("password") String aPassword) {
		return Response.ok().build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Person fromJSON(String aString) {
		Person iResponse = new Person();
		iResponse.setFirstName("Toto");
		iResponse.setLastName("DUPONT");
		return iResponse ;
	}

}
