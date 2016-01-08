/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.Files;
import com.maohi.software.maohifx.common.server.HibernateUtil;

/**
 * @author heifara
 *
 */
@Path("reports")
public class ReportService extends RestService {

	@PermitAll
	@GET
	@Path("/{reportId:.*}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response reports(@PathParam("reportId") final String areportId, @Context final UriInfo aUriInfo) {
		try {
			final URL iUrl = new URL(this.getLocalContextUri() + "/" + areportId + "/report.sql");
			final String iStatement = Files.toString(iUrl);

			final MultivaluedMap<String, String> iQueryParameters = aUriInfo.getQueryParameters();

			final List<?> iElements = this.reportsResult(iStatement, iQueryParameters);

			final String iJSONObject = new ObjectMapper().writeValueAsString(iElements);
			return Response.ok(iJSONObject).header("fxml", "false").build();
		} catch (final IOException aException) {
			return Response.serverError().build();
		}
	}

	private List<?> reportsResult(final String aStatement, final MultivaluedMap<String, String> aQueryParameters) {
		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		try {
			final Query iQuery = iSession.createSQLQuery(aStatement.toString());
			final List<?> iElements = iQuery.list();
			return iElements;
		} catch (final Exception aException) {
			throw aException;
		} finally {
			iSession.close();
		}
	}

}
