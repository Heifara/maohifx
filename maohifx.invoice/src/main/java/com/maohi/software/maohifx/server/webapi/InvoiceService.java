/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;

/**
 * @author heifara
 *
 */
@Path("invoice")
public class InvoiceService {

	@Context
	ServletContext context;

	public InvoiceService() {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoicePaymentLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(PaymentMode.class);
		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response fromQueryParam(@QueryParam("uuid") final String aUuid) {
		try {
			final InvoiceDAO iInvoiceDAO = new InvoiceDAO();
			final Invoice iInvoice = iInvoiceDAO.read(aUuid);
			final String iJSONObject = new ObjectMapper().writeValueAsString(iInvoice);
			return Response.ok(iJSONObject).build();
		} catch (final IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("pdf")
	@Produces({ "application/pdf" })
	public Response pdf() {
		try {
			return Response.seeOther(new URL("http://localhost:8080/maohifx.server/my.pdf").toURI()).build();
		} catch (MalformedURLException | URISyntaxException e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(final String aJSONObject) {
		Invoice iInvoice = null;
		try {
			iInvoice = new ObjectMapper().readValue(aJSONObject, Invoice.class);
			if (iInvoice.getUuid() == null) {
				iInvoice.setUuid(UUID.randomUUID().toString());
				iInvoice.bindChildren();

				final InvoiceDAO iInvoiceDAO = new InvoiceDAO();
				iInvoiceDAO.beginTransaction();
				iInvoice.setNumber(iInvoiceDAO.next(Integer.class, "number"));
				iInvoiceDAO.insert(iInvoice);
				iInvoiceDAO.commit();
			} else {
				iInvoice.bindChildren();

				final InvoiceDAO iInvoiceDAO = new InvoiceDAO();
				iInvoiceDAO.beginTransaction();
				iInvoiceDAO.update(iInvoice);
				iInvoiceDAO.commit();
			}

			final String iJSONObject = new ObjectMapper().writeValueAsString(iInvoice);
			final Response iResponse = Response.ok(iJSONObject).build();

			return iResponse;
		} catch (final JsonParseException aException) {
			aException.printStackTrace();
			return Response.serverError().entity(aException.getMessage()).build();
		} catch (final JsonMappingException aException) {
			aException.printStackTrace();
			return Response.serverError().entity(aException.getMessage()).build();
		} catch (final IOException aException) {
			aException.printStackTrace();
			return Response.serverError().entity(aException.getMessage()).build();
		}

	}

}
