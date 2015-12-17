/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.AnnotatedClass;

/**
 * @author heifara
 *
 */
public abstract class AnnotatedClassService<A extends AbstractDAO<T>, T extends AnnotatedClass> extends RestService {
	protected final A dao;

	public AnnotatedClassService() throws InstantiationException, IllegalAccessException {
		this.dao = this.getDAOClass().newInstance();
	}

	abstract Class<T> getAnnotatedClass();

	abstract Class<A> getDAOClass();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFromQueryParam(@QueryParam("uuid") final String aUuid) {
		try {
			final T iElement = this.dao.read(aUuid);
			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public abstract void onInserted(T iElement);

	public abstract void onInserting(T iElement);

	public abstract void onSaved(T iElement);

	public abstract void onSaving(T iElement);

	public abstract void onUpdated(T iElement);

	public abstract void onUpdating(T iElement);

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(final String aJSONObject) {
		T iElement;
		try {
			iElement = new ObjectMapper().readValue(aJSONObject, this.getAnnotatedClass());

			this.onSaving(iElement);

			if (iElement.getUuid() == null) {
				iElement.setUuid(UUID.randomUUID().toString());
				iElement.setCreationDate(new Date());
				iElement.setUpdateDate(new Date());

				this.dao.beginTransaction();
				this.onInserting(iElement);
				this.dao.insert(iElement);
				this.dao.commit();
				this.onInserted(iElement);
			} else {
				iElement.setUpdateDate(new Date());

				this.dao.beginTransaction();
				this.onUpdating(iElement);
				this.dao.update(iElement);
				this.dao.commit();
				this.onUpdated(iElement);
			}

			this.onSaved(iElement);

			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	abstract public List<T> search(String aPattern);

	@Path("search")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchFromQueryParam(@QueryParam("pattern") final String aPattern) {
		try {
			final List<T> iElements = this.search(aPattern);
			for (final T iElement : iElements) {
				iElement.setHref(this.getLocalContextUri() + "/webapi/" + this.getAnnotatedClass().getSimpleName().toLowerCase() + "?uuid=" + iElement.getUuid());
			}
			final String iJSONObject = new ObjectMapper().writeValueAsString(iElements);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
