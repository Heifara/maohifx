/**
 *
 */
package com.maohi.software.maohifx.contact.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.contact.bean.Salesman;

/**
 * @author heifara
 *
 */
public class SalesmanDAO extends AbstractDAO<Salesman> {

	@Override
	public Class<Salesman> getAnnotatedClass() {
		return Salesman.class;
	}

}
