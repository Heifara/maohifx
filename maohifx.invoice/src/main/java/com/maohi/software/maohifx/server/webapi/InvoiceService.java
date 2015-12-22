/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;
import com.maohi.software.maohifx.invoice.jaxb2.Customer;

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

}
