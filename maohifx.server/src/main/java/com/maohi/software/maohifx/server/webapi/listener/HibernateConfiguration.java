/**
 *
 */
package com.maohi.software.maohifx.server.webapi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.maohi.software.maohifx.common.server.HibernateUtil;
import com.maohi.software.maohifx.contact.bean.Contact;
import com.maohi.software.maohifx.contact.bean.Customer;
import com.maohi.software.maohifx.contact.bean.Email;
import com.maohi.software.maohifx.contact.bean.Phone;
import com.maohi.software.maohifx.contact.bean.Salesman;
import com.maohi.software.maohifx.contact.bean.Supplier;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.bean.Tva;
import com.maohi.software.maohifx.product.bean.Product;

/**
 * @author heifara
 *
 */
public class HibernateConfiguration implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent aServletContextEvent) {
	}

	@Override
	public void contextInitialized(final ServletContextEvent aServletContextEvent) {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoicePaymentLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(PaymentMode.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Product.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Tva.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Customer.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Supplier.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Contact.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Email.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Phone.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Salesman.class);
	}
}
