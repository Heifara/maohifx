/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

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
@Path("get")
public class Get {

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response fromJSON(@QueryParam("id") String aId) {
		return Response.ok().entity(aId).build();
	}

}
