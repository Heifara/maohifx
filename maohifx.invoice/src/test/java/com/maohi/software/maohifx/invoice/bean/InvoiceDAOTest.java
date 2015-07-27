package com.maohi.software.maohifx.invoice.bean;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.maohi.software.maohifx.common.AbstractDAO;
import com.maohi.software.maohifx.common.HibernateUtil;

/**
 * @author heifara
 *
 */
public class InvoiceDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.getConfiguration().addAnnotatedClass(Invoice.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(InvoiceLine.class);
		Session iSession = HibernateUtil.getSessionFactory().openSession();
		AbstractDAO.setSession(iSession);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldCRUD() throws Exception {
		final Invoice iInvoice = new Invoice(UUID.randomUUID().toString());
		final InvoiceDAO iDAO = new InvoiceDAO();

		iDAO.beginTransaction();
		iDAO.insert(iInvoice);
		iDAO.commit();

		final Invoice iInvoice2 = iDAO.read(iInvoice.getUuid());
		assertTrue(iInvoice.equals(iInvoice2));

		iDAO.beginTransaction();
		iDAO.update(iInvoice2);
		iDAO.commit();

		iDAO.beginTransaction();
		iDAO.delete(iInvoice);
		iDAO.commit();
	}

	@After
	public void tearDown() throws Exception {
	}

}
