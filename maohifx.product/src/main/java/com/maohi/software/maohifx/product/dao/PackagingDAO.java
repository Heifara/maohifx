/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.Packaging;

/**
 * @author heifara
 *
 */
public class PackagingDAO extends AbstractDAO<Packaging> {

	@Override
	public Class<Packaging> getAnnotatedClass() {
		return Packaging.class;
	}

}
