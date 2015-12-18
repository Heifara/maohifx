/**
 *
 */
package com.maohi.software.maohifx.contact.dao;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.contact.bean.Supplier;

/**
 * @author heifara
 *
 */
public class SupplierDAO extends AbstractDAO<Supplier> {

	@Override
	public Class<Supplier> getAnnotatedClass() {
		return Supplier.class;
	}

}
