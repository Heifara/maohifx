/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
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
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.bean.TvaReport;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;
import com.maohi.software.maohifx.invoice.dao.InvoiceLineDAO;
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;
import com.maohi.software.maohifx.invoice.jaxb2.Customer;
import com.maohi.software.maohifx.product.ProductPackagingMovementManager;
import com.maohi.software.maohifx.product.bean.ProductPackaging;

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
	protected String getJaxbPackage() {
		return "com.maohi.software.maohifx.invoice.jaxb2";
	}

	@Override
	protected InputStream getXslInputStream(final Invoice iElement) {
		return this.getClass().getClassLoader().getResourceAsStream("invoice.xsl");
	}

	@Override
	public void onInserted(final Invoice iElement) {
	}

	@Override
	public void onInserting(final Invoice iElement) {
		iElement.setNumber(this.dao.next(Integer.class, "number"));

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

	@Override
	public List<Invoice> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final Invoice iElement) {
		// Set Invoice Header info
		final com.maohi.software.maohifx.invoice.jaxb2.Invoice iJaxbInvoice = new com.maohi.software.maohifx.invoice.jaxb2.Invoice();
		iJaxbInvoice.setNumber(iElement.getNumber().toString());
		iJaxbInvoice.setDate(DateFormat.getDateInstance().format(new Date()));
		iJaxbInvoice.setTvaAmount(iElement.getTvaAmount());
		iJaxbInvoice.setDiscountAmount(iElement.getDiscountAmount());
		iJaxbInvoice.setTotalWithNoTaxAmount(iElement.getTotalWithNoTaxAmount());
		iJaxbInvoice.setTotalAmount(iElement.getTotalAmount());

		// Set Customer info
		iJaxbInvoice.setCustomer(new Customer());
		iJaxbInvoice.getCustomer().setName(iElement.getCustomer().getContact().getFullName());
		iJaxbInvoice.getCustomer().setCity("Papeete");
		iJaxbInvoice.getCustomer().setAddress("Rue du commandant Destremaux, pk0");
		iJaxbInvoice.getCustomer().setPhone("+(689) 87 86 52 69 52");

		// Set Lines info
		iJaxbInvoice.setInvoiceLines(new com.maohi.software.maohifx.invoice.jaxb2.Invoice.InvoiceLines());
		for (final InvoiceLine iInvoiceLine : iElement.getInvoiceLines()) {
			final com.maohi.software.maohifx.invoice.jaxb2.InvoiceLine iJaxbInvoiceLine = new com.maohi.software.maohifx.invoice.jaxb2.InvoiceLine();
			iJaxbInvoice.getInvoiceLines().getInvoiceLine().add(iJaxbInvoiceLine);

			iJaxbInvoiceLine.setBarCode("");
			iJaxbInvoiceLine.setLabel(iInvoiceLine.getLabel());
			iJaxbInvoiceLine.setQuantity(iInvoiceLine.getQuantity());
			iJaxbInvoiceLine.setSellingPrice(iInvoiceLine.getSellingPrice());
			iJaxbInvoiceLine.setTvaRate(iInvoiceLine.getTvaRate());
			iJaxbInvoiceLine.setDiscountRate(iInvoiceLine.getDiscountRate());
			iJaxbInvoiceLine.setTotal(iInvoiceLine.getTotalAmount());
		}

		// Set Payment info
		iJaxbInvoice.setInvoicePaymentLines(new com.maohi.software.maohifx.invoice.jaxb2.Invoice.InvoicePaymentLines());
		for (final InvoicePaymentLine iInvoicePaymentLine : iElement.getInvoicePaymentLines()) {
			final com.maohi.software.maohifx.invoice.jaxb2.InvoicePaymentLine iJaxbInvoicePaymentLine = new com.maohi.software.maohifx.invoice.jaxb2.InvoicePaymentLine();
			iJaxbInvoice.getInvoicePaymentLines().getInvoicePaymentLine().add(iJaxbInvoicePaymentLine);

			iJaxbInvoicePaymentLine.setLabel(iInvoicePaymentLine.getPaymentMode().getLabel());
			iJaxbInvoicePaymentLine.setAmount(iInvoicePaymentLine.getAmount());
		}
		return iJaxbInvoice;
	}

	@GET
	@Path("tvaReport")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response tvaReportFromQueryParam(@QueryParam("start") final String aStart, @QueryParam("end") final String aEnd) {
		try {
			final List<TvaReport> iReports = new InvoiceLineDAO().tvaReport(aStart, aEnd);
			return Response.ok(iReports).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed("user")
	@POST
	@Path("/valid")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response valid(final String aJSONObject) {
		try {
			final Invoice iElement = new ObjectMapper().readValue(aJSONObject, this.getAnnotatedClass());
			iElement.bindChildren();
			for (final InvoiceLine iInvoiceLine : iElement.getInvoiceLines()) {
				final ProductPackaging iProductPackaging = iInvoiceLine.getProductPackaging();
				if (iInvoiceLine.getQuantity() > 0) {
					ProductPackagingMovementManager.out(iProductPackaging.getId().getProductUuid(), iProductPackaging.getId().getPackagingCode(), iInvoiceLine.getQuantity());
				} else {
					ProductPackagingMovementManager.entry(iProductPackaging.getId().getProductUuid(), iProductPackaging.getId().getPackagingCode(), iInvoiceLine.getQuantity());
				}
			}

			this.dao.beginTransaction();
			iElement.setValidDate(new Date());
			this.dao.update(iElement);
			this.dao.commit();

			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
