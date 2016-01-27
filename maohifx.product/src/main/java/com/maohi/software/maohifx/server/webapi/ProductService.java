/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.maohi.software.maohifx.product.ProductPackagingMovementManager;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.bean.ProductPackaging;
import com.maohi.software.maohifx.product.dao.ProductDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingMovementDAO;

/**
 * @author heifara
 *
 */
@Path("product")
public class ProductService extends AnnotatedClassService<ProductDAO, Product> {

	private final ProductPackagingDAO productPackagingDAO;
	private final ProductPackagingMovementDAO productPackagingMovementDAO;

	public ProductService() throws InstantiationException, IllegalAccessException {
		super();

		this.productPackagingDAO = new ProductPackagingDAO();
		this.productPackagingMovementDAO = new ProductPackagingMovementDAO();
	}

	@Override
	Class<Product> getAnnotatedClass() {
		return Product.class;
	}

	@Override
	Class<ProductDAO> getDAOClass() {
		return ProductDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final Product iElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param aProductUuid
	 * @param aPackagingCode
	 * @param aQuantities
	 * @return
	 * @throws IOException
	 * @see http://localhost:8080/maohifx.server/webapi/product/entry?productUuid=938d6e11-1d68-41d7-a2cb-75cdc2cc645f&packagingCode=UNT&quantities=1.0
	 */
	@Path("/entry")
	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response newEntry(@QueryParam("productUuid") final String aProductUuid, @QueryParam("packagingCode") final String aPackagingCode, @QueryParam("quantities") final Double aQuantities) throws IOException {
		final Product iProduct = this.dao.read(aProductUuid);
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
	@Path("/out")
	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response newOut(@QueryParam("productUuid") final String aProductUuid, @QueryParam("packagingCode") final String aPackagingCode, @QueryParam("quantities") final Double aQuantities) throws IOException {
		final Product iProduct = this.dao.read(aProductUuid);
		if (iProduct == null) {
			throw new NullPointerException(aProductUuid + " does not match any record in product");
		}

		ProductPackagingMovementManager.out(aProductUuid, aPackagingCode, aQuantities);
		return Response.ok().build();
	}

	@Override
	public void onInserted(final Product iElement) {
	}

	@Override
	public void onInserting(final Product iElement) {
		iElement.bindChildren();

		for (final ProductPackaging iProductPackaging : iElement.getProductPackagings()) {
			if (!this.productPackagingDAO.exists(iProductPackaging.getId())) {
				iProductPackaging.add(0.0, 0.0, null, 0.0);
			}
		}
	}

	@Override
	public void onSaved(final Product iElement) {

	}

	@Override
	public void onSaving(final Product iElement) {
	}

	@Override
	public void onUpdated(final Product iElement) {
	}

	@Override
	public void onUpdating(final Product iElement) {
		iElement.bindChildren();

		for (final ProductPackaging iProductPackaging : iElement.getProductPackagings()) {
			if (!this.productPackagingDAO.exists(iProductPackaging.getId())) {
				iProductPackaging.add(0.0, 0.0, null, 0.0);
			}
		}
	}

	@Path("/quantities")
	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response readQuantities(@QueryParam("uuid") final String aUuid) {
		try {
			final Product iProduct = this.dao.read(aUuid);
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

	@Override
	public List<Product> search(final String aPattern) {
		List<Product> iProducts = new ArrayList<>();
		if (aPattern.isEmpty()) {
			iProducts = this.dao.readAll();
		} else {
			iProducts = this.dao.readByDesignation(aPattern);
		}
		return iProducts;
	}

	@Override
	protected Object toJaxb(final Product iElement) {
		return null;
	}

}
