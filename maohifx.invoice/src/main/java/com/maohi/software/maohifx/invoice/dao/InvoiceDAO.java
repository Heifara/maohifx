/**
 * 
 */
package com.maohi.software.maohifx.invoice.dao;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.invoice.bean.Invoice;

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
