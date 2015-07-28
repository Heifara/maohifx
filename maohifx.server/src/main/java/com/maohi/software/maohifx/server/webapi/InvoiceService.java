/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceDAO;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;

/**
 * @author heifara
 *
 */
@Path("invoice")
@Produces({ MediaType.APPLICATION_JSON })
public class InvoiceService {

	public InvoiceService() {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(String aJSONObject) {
		Invoice iInvoice = null;
		try {
			iInvoice = new ObjectMapper().readValue(aJSONObject, Invoice.class);
			if (iInvoice.getUuid() == null) {
				iInvoice.setUuid(UUID.randomUUID().toString());
				InvoiceDAO iInvoiceDAO = new InvoiceDAO();
				iInvoiceDAO.beginTransaction();
				iInvoice.setNumber(iInvoiceDAO.next(Integer.class, "number"));
				iInvoiceDAO.insert(iInvoice);
				iInvoiceDAO.commit();
			} else {
				InvoiceDAO iInvoiceDAO = new InvoiceDAO();
				iInvoiceDAO.beginTransaction();
				iInvoiceDAO.update(iInvoice);
				iInvoiceDAO.commit();
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(iInvoice).build();
	}

}
