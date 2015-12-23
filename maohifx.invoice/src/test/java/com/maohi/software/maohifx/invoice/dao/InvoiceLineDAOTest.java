/**
 *
 */
package com.maohi.software.maohifx.invoice.dao;

import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
import com.maohi.software.maohifx.invoice.bean.Tva;
import com.maohi.software.maohifx.invoice.bean.TvaReport;

/**
 * @author heifara
 *
 */
public class InvoiceLineDAOTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoicePaymentLine.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(PaymentMode.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Tva.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Customer.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Supplier.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Contact.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Email.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Phone.class);

		final Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTvaReport() throws Exception {
		final List<TvaReport> iTvaReports = new InvoiceLineDAO().tvaReport("2015-12-01", "2015-12-30");
	}

}
