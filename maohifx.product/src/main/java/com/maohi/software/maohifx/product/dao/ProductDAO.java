/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import java.util.List;

import org.hibernate.Query;

import com.maohi.software.maohifx.common.server.AbstractDAO;
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

	@SuppressWarnings("unchecked")
	public List<Product> readByDesignation(final String aPattern) {
		final Query iQuery = session.createQuery("FROM Product WHERE designation LIKE :pattern");
		iQuery.setString("pattern", "%" + aPattern + "%");
		return iQuery.list();
	}

}
