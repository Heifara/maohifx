/**
 *
 */
package com.maohi.software.maohifx.invoice.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.invoice.bean.Tva;

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
