/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.bean.ProductPackaging;
import com.maohi.software.maohifx.product.bean.ProductPackagingLot;
import com.maohi.software.maohifx.product.dao.ProductDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingLotDAO;

/**
 * @author heifara
 *
 */
@Path("product")
public class ProductService extends AnnotatedClassService<ProductDAO, Product> {

	private final ProductPackagingDAO productPackagingDAO;
	private final ProductPackagingLotDAO productPackagingLotDAO;
	private final ArrayList<ProductPackagingLot> productPackagingLotToInsert;

	public ProductService() throws InstantiationException, IllegalAccessException {
		super();

		this.productPackagingDAO = new ProductPackagingDAO();
		this.productPackagingLotDAO = new ProductPackagingLotDAO();

		this.productPackagingLotToInsert = new ArrayList<>();
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
		for (final ProductPackagingLot iProductPackagingLot : this.productPackagingLotToInsert) {
			this.productPackagingLotDAO.insert(iProductPackagingLot);
		}
	}

	@Override
	public void onInserting(final Product iElement) {
		iElement.bindChildren();

		for (final ProductPackaging iProductPackaging : iElement.getProductPackagings()) {
			if (!this.productPackagingDAO.exists(iProductPackaging.getId())) {
				final ProductPackagingLot iProductPackagingLot = new ProductPackagingLot();
				iProductPackagingLot.parse(0, iProductPackaging, 0.0, 0.0, null);
				this.productPackagingLotToInsert.add(iProductPackagingLot);
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
		for (final ProductPackagingLot iProductPackagingLot : this.productPackagingLotToInsert) {
			this.productPackagingLotDAO.insert(iProductPackagingLot);
		}
	}

	@Override
	public void onUpdating(final Product iElement) {
		iElement.bindChildren();

		for (final ProductPackaging iProductPackaging : iElement.getProductPackagings()) {
			if (!this.productPackagingDAO.exists(iProductPackaging.getId())) {
				final int iLot = this.productPackagingLotDAO.next(Integer.class, "id.lot", String.format("WHERE id.productPackagingPackagingCode='%s' AND id.productPackagingProductUuid='%s'", iProductPackaging.getId().getPackagingCode(), iProductPackaging.getId().getProductUuid()));

				final ProductPackagingLot iProductPackagingLot = new ProductPackagingLot();
				iProductPackagingLot.parse(iLot, iProductPackaging, 0.0, 0.0, null);
				this.productPackagingLotToInsert.add(iProductPackagingLot);
			}
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
