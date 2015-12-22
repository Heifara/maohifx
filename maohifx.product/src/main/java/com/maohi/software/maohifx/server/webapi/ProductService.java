/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.dao.ProductDAO;

/**
 * @author heifara
 *
 */
@Path("product")
public class ProductService extends AnnotatedClassService<ProductDAO, Product> {

	public ProductService() throws InstantiationException, IllegalAccessException {
		super();
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

	@Override
	public void onInserted(final Product iElement) {

	}

	@Override
	public void onInserting(final Product iElement) {

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
