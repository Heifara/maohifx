/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author heifara
 *
 */
@Path("fxml")
public class Fxml {
	private final HashMap<String, String> fxmls;

	@Context
	ServletContext context;

	public Fxml() {
		this.fxmls = new HashMap<String, String>();
		this.fxmls.put("index", "index");
		this.fxmls.put("invoice", "invoice/index");
		this.fxmls.put("invoices", "invoices/index");
		this.fxmls.put("contact", "contact/index");
		this.fxmls.put("contacts", "contacts/index");
		this.fxmls.put("product", "product/index");
		this.fxmls.put("productPrices", "product/prices");
		this.fxmls.put("products", "products/index");
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response fromQueryParam(@QueryParam("id") final String aId) {
		return this.loadFxml(aId);
	}

	private String load(final URL aUrl) throws URISyntaxException, IOException {
		final StringBuilder iContent = new StringBuilder();

		final BufferedReader iBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(aUrl.toURI())), "UTF8"));
		String iLine;

		while ((iLine = iBufferedReader.readLine()) != null) {
			iContent.append(iLine);
		}

		iBufferedReader.close();
		return iContent.toString();
	}

	/**
	 * @param aId
	 * @return
	 */
	private Response loadFxml(final String aId) {
		try {
			return Response.ok().entity(this.load(new URL("file:///" + this.context.getRealPath("/" + this.fxmls.get(aId) + ".fxml").replace('\\', '/')))).build();
		} catch (URISyntaxException | IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
