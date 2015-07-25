/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.maohi.software.maohifx.invoice.bean.Invoice;

/**
 * @author heifara
 *
 */
@Path("invoice")
@Produces({ MediaType.APPLICATION_JSON })
public class InvoiceService {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(Invoice aInvoice) {
		return Response.ok(new Invoice()).build();
	}

}
