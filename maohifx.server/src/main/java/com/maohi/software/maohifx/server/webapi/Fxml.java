/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

	public Fxml() {
		this.fxmls = new HashMap<String, String>();
		this.fxmls.put("index", "index");
		this.fxmls.put("invoice", "invoice/index");
		this.fxmls.put("invoices", "invoices/index");
	}

	@Context
	ServletContext context;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response fromQueryParam(@QueryParam("id") String aId) {
		return loadFxml(aId);
	}

	/**
	 * @param aId
	 * @return
	 */
	private Response loadFxml(String aId) {
		try {
			return Response.ok().entity(load(new URL("file:///" + context.getRealPath("/" + this.fxmls.get(aId) + ".fxml").replace('\\', '/')))).build();
		} catch (URISyntaxException | IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String load(URL aUrl) throws URISyntaxException, IOException {
		StringBuilder iContent = new StringBuilder();

		BufferedReader iBufferedReqder = new BufferedReader(new FileReader(new File(aUrl.toURI())));
		String iLine;

		while ((iLine = iBufferedReqder.readLine()) != null) {
			iContent.append(iLine);
		}

		iBufferedReqder.close();
		return iContent.toString();
	}
}
