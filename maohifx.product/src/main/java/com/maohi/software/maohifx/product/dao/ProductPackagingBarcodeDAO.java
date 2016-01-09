/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.ProductPackagingBarcode;

/**
 * @author heifara
 *
 */
public class ProductPackagingBarcodeDAO extends AbstractDAO<ProductPackagingBarcode> {

	@Override
	public Class<ProductPackagingBarcode> getAnnotatedClass() {
		return ProductPackagingBarcode.class;
	}

}
