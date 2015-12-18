/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;

/**
 * @author heifara
 *
 */
@Path("invoice")
public class InvoiceService extends AnnotatedClassService<InvoiceDAO, Invoice> {

	public InvoiceService() throws InstantiationException, IllegalAccessException {
		super();
	}

	@Override
	Class<Invoice> getAnnotatedClass() {
		return Invoice.class;
	}

	@Override
	Class<InvoiceDAO> getDAOClass() {
		return InvoiceDAO.class;
	}

	@Override
	public void onInserted(final Invoice iElement) {
	}

	@Override
	public void onInserting(final Invoice iElement) {
		iElement.bindChildren();
	}

	@Override
	public void onSaved(final Invoice iElement) {
	}

	@Override
	public void onSaving(final Invoice iElement) {
		final PaymentModeDAO iDAO = new PaymentModeDAO();
		for (final InvoicePaymentLine iInvoicePaymentLine : iElement.getInvoicePaymentLines()) {
			final PaymentMode iPaymentMode = iInvoicePaymentLine.getPaymentMode();
			final PaymentMode iValidPaymentMode;
			switch (iPaymentMode.getLabel()) {
			case "CASH":
				iValidPaymentMode = iDAO.read(0);
				break;

			case "CHEQUE":
				iValidPaymentMode = iDAO.read(1);
				break;

			case "CARTE DE CREDIT":
				iValidPaymentMode = iDAO.read(2);
				break;

			default:
				iValidPaymentMode = null;
				break;
			}

			iInvoicePaymentLine.setPaymentMode(iValidPaymentMode);
		}
	}

	@Override
	public void onUpdated(final Invoice iElement) {
	}

	@Override
	public void onUpdating(final Invoice iElement) {
		iElement.bindChildren();
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

	@Override
	public List<Invoice> search(final String aPattern) {
		return this.dao.readAll();
	}

}
