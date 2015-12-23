/**
 *
 */
package com.maohi.software.maohifx.invoice.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;

/**
 * @author heifara
 *
 */
public class PaymentModeDAO extends AbstractDAO<PaymentMode> {

	@Override
	public Class<PaymentMode> getAnnotatedClass() {
		return PaymentMode.class;
	}

}
