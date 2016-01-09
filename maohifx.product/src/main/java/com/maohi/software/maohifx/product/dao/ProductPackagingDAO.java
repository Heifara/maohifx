/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.ProductPackaging;

/**
 * @author heifara
 *
 */
public class ProductPackagingDAO extends AbstractDAO<ProductPackaging> {

	@Override
	public Class<ProductPackaging> getAnnotatedClass() {
		return ProductPackaging.class;
	}

}
