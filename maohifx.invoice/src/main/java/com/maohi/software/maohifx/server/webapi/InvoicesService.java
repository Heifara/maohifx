/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

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
@Path("invoices")
@Produces({ MediaType.APPLICATION_JSON })
public class InvoicesService extends RestService {

	@GET
	public Response search(final String aPattern) {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoicePaymentLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(PaymentMode.class);
		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);

		final InvoiceDAO iDAO = new InvoiceDAO();
		final List<Invoice> iInvoices = iDAO.readAll();
		for (final Invoice iInvoice : iInvoices) {
			iInvoice.setHref(this.getLocalContextUri() + "/webapi/invoice?uuid=" + iInvoice.getUuid());
		}

		try {
			final String iJSONObject = new ObjectMapper().writeValueAsString(iInvoices);
			final Response iResponse = Response.ok(iJSONObject).build();
			return iResponse;
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.serverError().entity(aException.getMessage()).build();
		}
	}

}
