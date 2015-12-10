/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;

/**
 * @author heifara
 *
 */
@Path("invoices")
@Produces({ MediaType.APPLICATION_JSON })
public class InvoicesService {

	@Context
	ServletContext context;

	@GET
	public Response search(final String aPattern) {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);

		final InvoiceDAO iDAO = new InvoiceDAO();
		final List<Invoice> iInvoices = iDAO.readAll();

		try {
			String iJSONObject = new ObjectMapper().writeValueAsString(iInvoices);
			Response iResponse = Response.ok(iJSONObject).build();
			return iResponse;
		} catch (Exception aException) {
			aException.printStackTrace();
			return Response.serverError().entity(aException.getMessage()).build();
		}
	}

}
