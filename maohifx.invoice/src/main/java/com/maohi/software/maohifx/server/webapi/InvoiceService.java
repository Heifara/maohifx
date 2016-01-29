/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

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
import com.maohi.software.maohifx.common.server.DAOFactory;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.TvaReport;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;
import com.maohi.software.maohifx.invoice.dao.InvoiceLineDAO;
import com.maohi.software.maohifx.product.ProductPackagingMovementManager;
import com.maohi.software.maohifx.product.bean.ProductPackaging;

/**
 * @author heifara
 *
 */
@Path("/")
public class InvoiceService {

	private final InvoiceDAO invoiceDAO;
	private final InvoiceLineDAO invoiceLineDAO;

	public InvoiceService() throws InstantiationException, IllegalAccessException {
		this.invoiceDAO = (InvoiceDAO) DAOFactory.getInstance(Invoice.class);
		this.invoiceLineDAO = (InvoiceLineDAO) DAOFactory.getInstance(InvoiceLine.class);
	}

	@GET
	@Path("invoice/tvaReport")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response tvaReportFromQueryParam(@QueryParam("start") final String aStart, @QueryParam("end") final String aEnd) {
		try {
			final List<TvaReport> iReports = this.invoiceLineDAO.tvaReport(aStart, aEnd);
			return Response.ok(iReports).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed("user")
	@POST
	@Path("invoice/valid")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response valid(final String aJSONObject) {
		try {
			final Invoice iElement = new ObjectMapper().readValue(aJSONObject, Invoice.class);
			iElement.bindChildren();
			for (final InvoiceLine iInvoiceLine : iElement.getInvoiceLines()) {
				final ProductPackaging iProductPackaging = iInvoiceLine.getProductPackaging();
				if (iInvoiceLine.getQuantity() > 0) {
					ProductPackagingMovementManager.out(iProductPackaging.getId().getProductUuid(), iProductPackaging.getId().getPackagingCode(), iInvoiceLine.getQuantity());
				} else {
					ProductPackagingMovementManager.entry(iProductPackaging.getId().getProductUuid(), iProductPackaging.getId().getPackagingCode(), iInvoiceLine.getQuantity());
				}
			}

			this.invoiceDAO.beginTransaction();
			iElement.setValidDate(new Date());
			this.invoiceDAO.update(iElement);
			this.invoiceDAO.commit();

			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
