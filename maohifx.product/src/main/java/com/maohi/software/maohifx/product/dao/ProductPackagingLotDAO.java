/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.ProductPackagingLot;

/**
 * @author heifara
 *
 */
public class ProductPackagingLotDAO extends AbstractDAO<ProductPackagingLot> {

	@Override
	public Class<ProductPackagingLot> getAnnotatedClass() {
		return ProductPackagingLot.class;
	}

}
