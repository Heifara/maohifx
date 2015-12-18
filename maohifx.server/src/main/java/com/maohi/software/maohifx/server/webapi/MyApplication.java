/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.Date;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;
import com.maohi.software.maohifx.contact.bean.Contact;
import com.maohi.software.maohifx.contact.bean.Customer;
import com.maohi.software.maohifx.contact.bean.Email;
import com.maohi.software.maohifx.contact.bean.Phone;
import com.maohi.software.maohifx.contact.bean.Supplier;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.InvoicePaymentLine;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;
import com.maohi.software.maohifx.product.bean.Product;

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
		HibernateUtil.getConfiguration().addAnnotatedClass(Product.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Customer.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Supplier.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Contact.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Email.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Phone.class);

		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);

		this.insertPaymentMode(0, "CASH");
		this.insertPaymentMode(1, "CHEQUE");
		this.insertPaymentMode(2, "CARTE DE CREDIT");
	}

	public void insertPaymentMode(final int aId, final String aLabel) {
		final PaymentMode iPaymentMode = new PaymentMode();
		iPaymentMode.setId(aId);
		iPaymentMode.setLabel(aLabel);
		iPaymentMode.setCreationDate(new Date());
		iPaymentMode.setUpdateDate(new Date());

		final PaymentModeDAO iDAO = new PaymentModeDAO();
		iDAO.beginTransaction();
		iDAO.replace(iPaymentMode);
		iDAO.commit();
	}

}
