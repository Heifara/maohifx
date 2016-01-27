/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import org.hibernate.Session;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.ProductPackagingLot;
import com.maohi.software.maohifx.product.bean.ProductPackagingLotId;

/**
 * @author heifara
 *
 */
public class ProductPackagingLotDAO extends AbstractDAO<ProductPackagingLot> {

	public ProductPackagingLotDAO() {
	}

	public ProductPackagingLotDAO(final Session aSession) {
		this.session = aSession;
	}

	@Override
	public Class<ProductPackagingLot> getAnnotatedClass() {
		return ProductPackagingLot.class;
	}

	public ProductPackagingLot getCurrentLot(final String aProductUuid, final String aPackagingCode) {
		// TODO Auto-generated method stub
		return this.read(new ProductPackagingLotId(0, aPackagingCode, aProductUuid));
	}

}
