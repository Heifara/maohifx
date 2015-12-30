/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.Profile;

/**
 * @author heifara
 *
 */
@Path("authentication")
@Produces({ MediaType.APPLICATION_JSON })
public class AuthenticationService {

	@POST
	@PermitAll
	@Path("connect")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response connect(final String aJSONObject) {
		try {
			final Profile iElement = new ObjectMapper().readValue(aJSONObject, Profile.class);
			if (iElement.getUsername().equals(iElement.getPassword())) {
				return Response.ok().entity(iElement).build();
			} else {
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
		} catch (final Exception aException) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@PermitAll
	@Path("disconnect")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response disconnect(final String aJSONObject) {
		try {
			final Profile iElement = new ObjectMapper().readValue(aJSONObject, Profile.class);
			return Response.ok().entity(iElement).build();
		} catch (final Exception aException) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
