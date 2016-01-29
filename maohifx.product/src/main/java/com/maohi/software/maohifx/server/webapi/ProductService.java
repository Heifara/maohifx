/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.server.DAOFactory;
import com.maohi.software.maohifx.product.ProductPackagingMovementManager;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.bean.ProductPackagingMovement;
import com.maohi.software.maohifx.product.dao.ProductDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingMovementDAO;

/**
 * @author heifara
 *
 */
@Path("/")
public class ProductService {

	private final ProductDAO productDAO;
	private final ProductPackagingMovementDAO productPackagingMovementDAO;

	public ProductService() throws InstantiationException, IllegalAccessException {
		super();

		this.productDAO = (ProductDAO) DAOFactory.getInstance(Product.class);
		this.productPackagingMovementDAO = (ProductPackagingMovementDAO) DAOFactory.getInstance(ProductPackagingMovement.class);
	}

	/**
	 * @param aProductUuid
	 * @param aPackagingCode
	 * @param aQuantities
	 * @return
	 * @throws IOException
	 * @see http://localhost:8080/maohifx.server/webapi/product/entry?productUuid=938d6e11-1d68-41d7-a2cb-75cdc2cc645f&packagingCode=UNT&quantities=1.0
	 */
	@Path("product/entry")
	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response newEntry(@QueryParam("productUuid") final String aProductUuid, @QueryParam("packagingCode") final String aPackagingCode, @QueryParam("quantities") final Double aQuantities) throws IOException {
		final Product iProduct = this.productDAO.read(aProductUuid);
		if (iProduct == null) {
			throw new NullPointerException(aProductUuid + " does not match any record in product");
		}

		ProductPackagingMovementManager.entry(aProductUuid, aPackagingCode, aQuantities);
		return Response.ok().build();
	}

	/**
	 *
	 * @param aProductUuid
	 * @param aPackagingCode
	 * @param aQuantities
	 * @return
	 * @throws IOException
	 * @see http://localhost:8080/maohifx.server/webapi/product/out?productUuid=938d6e11-1d68-41d7-a2cb-75cdc2cc645f&packagingCode=UNT&quantities=1.0
	 */
	@Path("product/out")
	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response newOut(@QueryParam("productUuid") final String aProductUuid, @QueryParam("packagingCode") final String aPackagingCode, @QueryParam("quantities") final Double aQuantities) throws IOException {
		final Product iProduct = this.productDAO.read(aProductUuid);
		if (iProduct == null) {
			throw new NullPointerException(aProductUuid + " does not match any record in product");
		}

		ProductPackagingMovementManager.out(aProductUuid, aPackagingCode, aQuantities);
		return Response.ok().build();
	}

	@Path("product/quantities")
	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response readQuantities(@QueryParam("uuid") final String aUuid) {
		try {
			final Product iProduct = this.productDAO.read(aUuid);
			if (iProduct == null) {
				throw new NullPointerException(aUuid + " does not match any record in product");
			}

			final Map<String, Object> iObject = new HashMap<>();
			iObject.put("availableQuantity", this.productPackagingMovementDAO.getQuantities(iProduct, null, null));
			iObject.put("currentQuantity", this.productPackagingMovementDAO.getQuantities(iProduct, null, 0));
			final String iJSONObject = new ObjectMapper().writeValueAsString(iObject);
			return Response.ok(iJSONObject).build();
		} catch (final IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
