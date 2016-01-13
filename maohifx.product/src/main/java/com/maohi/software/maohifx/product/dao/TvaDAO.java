/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.Tva;

/**
 * @author heifara
 *
 */
public class TvaDAO extends AbstractDAO<Tva> {

	@Override
	public Class<Tva> getAnnotatedClass() {
		return Tva.class;
	}

}
