/**
 *
 */
package com.maohi.software.maohifx.contact.dao;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.contact.bean.Customer;

/**
 * @author heifara
 *
 */
public class CustomerDAO extends AbstractDAO<Customer> {

	@Override
	public Class<Customer> getAnnotatedClass() {
		return Customer.class;
	}

}
