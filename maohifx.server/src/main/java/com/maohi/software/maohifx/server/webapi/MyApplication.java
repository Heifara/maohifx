/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;

/**
 * @author heifara
 *
 */
public class MyApplication extends ResourceConfig {

	public MyApplication() {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoicePaymentLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(PaymentMode.class);

		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);
	}

}
