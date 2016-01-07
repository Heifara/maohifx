/**
 *
 */
package com.maohi.software.maohifx.server.webapi.listener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;

import com.maohi.software.maohifx.common.server.AbstractDAO;
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
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;
import com.maohi.software.maohifx.invoice.dao.TvaDAO;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.server.webapi.MyApplication;

/**
 * @author heifara
 *
 */
public class HibernateConfiguration implements ServletContextListener, Runnable {

	@Override
	public void contextDestroyed(final ServletContextEvent aServletContextEvent) {
	}

	@Override
	public void contextInitialized(final ServletContextEvent aServletContextEvent) {
		try {
			HibernateUtil.setConfigurationURL(new URL("http://localhost:8080/maohifx.configure/hibernate.cfg.xml"));

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

			MyApplication.runLater(this);
		} catch (MalformedURLException aException) {
			aException.printStackTrace();
		}
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

	private void insertTva(final Integer aType, final String aLabel, final Double aRate) {
		final Tva iTva = new Tva(aType);
		iTva.setLabel(aLabel);
		iTva.setRate(aRate);
		final TvaDAO iDao = new TvaDAO();
		iDao.beginTransaction();
		iDao.replace(iTva);
		iDao.commit();
	}

	@Override
	public void run() {
		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);

		this.insertPaymentMode(0, "CASH");
		this.insertPaymentMode(1, "CHEQUE");
		this.insertPaymentMode(2, "CARTE DE CREDIT");

		this.insertTva(0, "PPN", 0.0);
		this.insertTva(1, "Service", 13.0);
		this.insertTva(2, "Produits 1", 6.0);
		this.insertTva(3, "Produits 2", 16.0);

		AbstractDAO.setSession(null);
		iSession.close();
	}
}
