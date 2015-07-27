/**
 * 
 */
package com.maohi.software.maohifx.invoice.bean;

import com.maohi.software.maohifx.common.AbstractDAO;

/**
 * @author heifara
 *
 */
public class InvoiceDAO extends AbstractDAO<Invoice> {

	@Override
	public Class<Invoice> getAnnotatedClass() {
		return Invoice.class;
	}

}
