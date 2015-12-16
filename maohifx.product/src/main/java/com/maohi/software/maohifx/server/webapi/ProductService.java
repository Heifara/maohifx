/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.dao.ProductDAO;

/**
 * @author heifara
 *
 */
@Path("product")
public class ProductService extends RestService {

	public ProductService() {
		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFromQueryParam(@QueryParam("uuid") final String aUuid) {
		try {
			final ProductDAO iDAO = new ProductDAO();
			final Product iElement = iDAO.read(aUuid);
			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(final String aJSONObject) {
		Product iProduct;
		try {
			iProduct = new ObjectMapper().readValue(aJSONObject, Product.class);
			if (iProduct.getUuid() == null) {
				iProduct.setUuid(UUID.randomUUID().toString());
				iProduct.setCreationDate(new Date());
				iProduct.setUpdateDate(new Date());

				final ProductDAO iDAO = new ProductDAO();
				iDAO.beginTransaction();
				iDAO.insert(iProduct);
				iDAO.commit();
			} else {
				iProduct.setUpdateDate(new Date());

				final ProductDAO iDAO = new ProductDAO();
				iDAO.beginTransaction();
				iDAO.update(iProduct);
				iDAO.commit();
			}

			final String iJSONObject = new ObjectMapper().writeValueAsString(iProduct);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("search")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchFromQueryParam(@QueryParam("pattern") final String aPattern) {
		try {
			final ProductDAO iDAO = new ProductDAO();
			final List<Product> iResults = iDAO.readAll();
			for (final Product iProduct : iResults) {
				iProduct.setHref(this.getLocalContextUri() + "/webapi/product?uuid=" + iProduct.getUuid());
			}

			final String iJSONObject = new ObjectMapper().writeValueAsString(iResults);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
