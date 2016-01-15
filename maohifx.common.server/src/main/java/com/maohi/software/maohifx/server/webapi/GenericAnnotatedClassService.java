/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.server.HibernateUtil;

/**
 * @author heifara
 *
 */
@Path("/generic")
public class GenericAnnotatedClassService {

	@PermitAll
	@RolesAllowed("user")
	@Path("{entity:.*}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generic(@PathParam("entity") final String aEntity, @QueryParam("action") final String aAction, @QueryParam("where") final String aWhere) {
		try {
			if ("getAll".equals(aAction)) {
				final Session iSession = HibernateUtil.getSessionFactory().openSession();

				final Query iQuery = iSession.createQuery(String.format("FROM %s %s", aEntity, aWhere != null ? "WHERE " + aWhere : ""));
				final List iElements = iQuery.list();

				final String iJSONObject = new ObjectMapper().writeValueAsString(iElements);
				return Response.ok(iJSONObject).build();
			} else {
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
		} catch (final JsonMappingException aException) {
			System.err.println(aException.getMessage());
			return Response.serverError().build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.serverError().build();
		}
	}

}
