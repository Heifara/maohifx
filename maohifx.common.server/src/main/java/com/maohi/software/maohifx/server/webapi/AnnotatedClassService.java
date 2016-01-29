/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.Strings;
import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.common.server.AnnotatedClass;
import com.maohi.software.maohifx.common.server.DAOFactory;
import com.maohi.software.maohifx.common.server.HibernateUtil;
import com.maohi.software.maohifx.common.server.PDFBuilderFactory;

/**
 * @author heifara
 *
 */
@Path("/")
public class AnnotatedClassService extends RestService {

	@SuppressWarnings("rawtypes")
	@PermitAll
	@Path("{entity}/get")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response get(@PathParam("entity") final String aEntity, @QueryParam("uuid") final String aUuid) {
		final Session iSession = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			// Resolve the class from entity name
			final Class<?> iClass = HibernateUtil.getAnnotatedClass(Strings.toUnderscore(aEntity));
			if (iClass != null) {

				// Read element from database
				final AbstractDAO iDAO = DAOFactory.getInstance(iClass);
				Object iElement = null;
				if (iDAO != null) {
					iElement = iDAO.read(aUuid);
				} else {
					iElement = iSession.get(iClass, aUuid);
				}

				if (iElement != null) {
					final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
					return Response.ok(iJSONObject).build();
				}
			}

			return Response.status(Status.EXPECTATION_FAILED).build();
		} catch (final JsonMappingException aException) {
			System.err.println(aException.getMessage());
			return Response.serverError().build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.serverError().build();
		} finally {
			iSession.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@PermitAll
	@Path("{entity}/getAll")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAll(@PathParam("entity") final String aEntity, @QueryParam("where") final String aWhere) {
		final Session iSession = HibernateUtil.getSessionFactory().openSession();

		List<?> iElements = null;
		try {
			final Class<?> iClass = HibernateUtil.getAnnotatedClass(Strings.toUnderscore(aEntity));
			if (iClass != null) {
				final AbstractDAO iDAO = DAOFactory.getInstance(iClass);
				if (iDAO != null) {
					iElements = iDAO.readAll(aWhere);
				} else {
					final Query iQuery = iSession.createQuery(String.format("FROM %s %s", iClass.getName(), aWhere != null ? "WHERE " + aWhere : ""));
					iElements = iQuery.list();
				}

				for (final Object iElement : iElements) {
					if (iElement instanceof AnnotatedClass) {
						final AnnotatedClass iAnnotatedClass = (AnnotatedClass) iElement;
						iAnnotatedClass.setHref(this.getLocalContextUri() + "/webapi/" + iClass.getSimpleName().toLowerCase() + "/get?uuid=" + iAnnotatedClass.getUuid());
					}
				}

				final String iJSONObject = new ObjectMapper().writeValueAsString(iElements);
				return Response.ok(iJSONObject).build();
			}

			return Response.status(Status.EXPECTATION_FAILED).build();
		} catch (final JsonMappingException aException) {
			System.err.println(aException.getMessage());
			return Response.serverError().build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.serverError().build();
		} finally {
			iSession.close();
		}
	}

	@PermitAll
	@Path("{entity}/pdf")
	@GET
	@Produces({ "application/pdf" })
	public Response pdf(@PathParam("entity") final String aEntity, @QueryParam("uuid") final String aUuid) {
		if ((this.uriInfo.getPathParameters().size() == 1) && (aUuid != null) && !aUuid.isEmpty()) {
			return this.pdfFromUuid(aEntity, aUuid);
		} else {
			return this.pdfFromId(aEntity);
		}
	}

	@PermitAll
	@Path("{entity}/pdf")
	@POST
	@Produces({ "application/pdf" })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response pdfFromData(@PathParam("entity") final String aEntity, final String aData) {
		try {
			// Resolve the class from the entity name
			final Class<?> iClass = HibernateUtil.getAnnotatedClass(Strings.toUnderscore(aEntity));
			if (iClass != null) {

				// Create the element from data
				final Object iElement = new ObjectMapper().readValue(aData, iClass);
				return Response.ok(PDFBuilderFactory.getInstance(aEntity).entity(iElement).output()).build();
			}

			return Response.status(Status.EXPECTATION_FAILED).build();
		} catch (FOPException | IOException | TransformerException | InstantiationException | IllegalAccessException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@SuppressWarnings("rawtypes")
	public Response pdfFromId(@PathParam("entity") final String aEntity) {
		try {
			// Resolve the class from entity name
			final Class<?> iClass = HibernateUtil.getAnnotatedClass(Strings.toUnderscore(aEntity));
			if (iClass != null) {

				// Resolve the id type from the annotated class
				final Class<? extends Serializable> iIdClass = HibernateUtil.getAnnotatedClassIdType(iClass);
				if (iIdClass != null) {

					// Create the id from the query parameter and the id type
					final Serializable iId = new ObjectMapper().readValue(this.queryParametersToJSON(), iIdClass);
					if (iId != null) {

						// Get DAO instance from Factory
						final AbstractDAO iDAO = DAOFactory.getInstance(iClass);
						if (iDAO != null) {

							// Read element from database
							final Object iElement = iDAO.read(iId);
							if (iElement != null) {
								return Response.ok(PDFBuilderFactory.getInstance(aEntity).entity(iElement).output()).build();
							}
						}
					}
				}
			}

			return Response.status(Status.EXPECTATION_FAILED).build();
		} catch (FOPException | IOException | TransformerException | InstantiationException | IllegalAccessException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@SuppressWarnings("rawtypes")
	public Response pdfFromUuid(final String aEntity, final String aUuid) {
		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		try {
			// Resolve the class from entity name
			final Class<?> iClass = HibernateUtil.getAnnotatedClass(Strings.toUnderscore(aEntity));
			if (iClass != null) {

				// Read element from database
				final AbstractDAO iDAO = DAOFactory.getInstance(iClass);
				Object iElement = null;
				if (iDAO != null) {
					iElement = iDAO.read(aUuid);
				} else {
					iElement = iSession.get(iClass, aUuid);
				}

				if (iElement != null) {
					return Response.ok(PDFBuilderFactory.getInstance(aEntity).entity(iElement).output()).build();
				}
			}

			return Response.status(Status.EXPECTATION_FAILED).build();
		} catch (FOPException | IOException | TransformerException | InstantiationException | IllegalAccessException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			iSession.close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RolesAllowed("user")
	@Path("{entity}/save")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(@PathParam("entity") final String aEntity, final String aData) {
		final Session iSession = HibernateUtil.getSessionFactory().openSession();

		try {
			// Resolve the class from entity name
			final Class<?> iClass = HibernateUtil.getAnnotatedClass(Strings.toUnderscore(aEntity));
			if (iClass != null) {

				// Create the element from data
				final Object iElement = new ObjectMapper().readValue(aData, iClass);
				final AbstractDAO iDAO = DAOFactory.getInstance(iClass);
				if (iDAO != null) {
					iDAO.beginTransaction();
					final Serializable iId = HibernateUtil.getAnnotatedClassId(iElement);
					if (!iDAO.exists(iId)) {
						iDAO.insert(iElement);
					} else {
						iDAO.update(iElement);
					}
					iDAO.commit();
				} else {
					iSession.beginTransaction();
					iSession.saveOrUpdate(iElement);
					iSession.getTransaction().commit();
				}

				final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
				return Response.ok(iJSONObject).build();
			} else {
				return Response.serverError().build();
			}
		} catch (final JsonMappingException aException) {
			System.err.println(aException.getMessage());
			return Response.serverError().build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.serverError().build();
		} finally {
			iSession.close();
		}
	}

	@PermitAll
	@Path("{entity}/search")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response search(@PathParam("entity") final String aEntity, @QueryParam("pattern") final String aWhere) {
		return this.getAll(aEntity, aWhere.isEmpty() ? null : aWhere);
	}
}
