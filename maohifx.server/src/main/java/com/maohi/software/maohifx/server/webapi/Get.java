/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author heifara
 *
 */
@PermitAll
@Path("get")
public class Get {

	@RolesAllowed("user")
	@GET
	@Path("authorized")
	public Response authorized() {
		return Response.ok().build();
	}

	@DenyAll
	@GET
	@Path("forbidden")
	public Response forbidden() {
		return Response.ok().build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response fromJSON(@QueryParam("id") final String aId) {
		return Response.ok().entity(aId).build();
	}

}
