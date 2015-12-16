/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.product.bean.Product;

/**
 * @author heifara
 *
 */
public class ProductDAO extends AbstractDAO<Product> {

	@Override
	public Class<Product> getAnnotatedClass() {
		return Product.class;
	}

}
