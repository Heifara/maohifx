/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import static org.junit.Assert.assertFalse;

import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.maohi.software.maohifx.common.server.HibernateUtil;
import com.maohi.software.maohifx.contact.bean.Contact;
import com.maohi.software.maohifx.contact.bean.Customer;
import com.maohi.software.maohifx.contact.bean.Email;
import com.maohi.software.maohifx.contact.bean.Phone;
import com.maohi.software.maohifx.contact.bean.Salesman;
import com.maohi.software.maohifx.contact.bean.Supplier;
import com.maohi.software.maohifx.product.bean.Barcode;
import com.maohi.software.maohifx.product.bean.Packaging;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.bean.ProductPackaging;
import com.maohi.software.maohifx.product.bean.ProductPackagingBarcode;
import com.maohi.software.maohifx.product.bean.ProductPackagingLot;
import com.maohi.software.maohifx.product.bean.ProductPackagingMovement;
import com.maohi.software.maohifx.product.bean.Tva;

/**
 * @author heifara
 *
 */
public class ProductPackagingLotDAOTest {

	private static Session session;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HibernateUtil.setConfigurationURL(new URL("file:///Conf/Dev/github/maohifx/maohifx.configure/src/main/webapp/hibernate.cfg.xml"));

		HibernateUtil.getConfiguration().addAnnotatedClass(Contact.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Customer.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Email.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Phone.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Salesman.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Supplier.class);

		HibernateUtil.getConfiguration().addAnnotatedClass(Barcode.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Packaging.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Product.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(ProductPackaging.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(ProductPackagingLot.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(ProductPackagingMovement.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(ProductPackagingBarcode.class);
		HibernateUtil.getConfiguration().addAnnotatedClass(Tva.class);

		session = HibernateUtil.getSessionFactory().openSession();
		ProductDAO.setSession(session);
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

	@Test
	public void shouldCRUD() throws Exception {
		final ProductDAO iProductDao = new ProductDAO();

		final Tva iTva = new Tva(1, new Date(), new Date(), "JUnit Test", 5.0);
		session.beginTransaction();
		session.saveOrUpdate(iTva);
		session.getTransaction().commit();

		final Packaging iPackaging = new Packaging("UNT");
		iPackaging.setCreationDate(new Date());
		iPackaging.setUpdateDate(new Date());
		iPackaging.setProductPackagings(new HashSet<>());
		session.beginTransaction();
		session.saveOrUpdate(iPackaging);
		session.getTransaction().commit();

		final Product iProduct = new Product();
		iProduct.setUuid(UUID.randomUUID().toString());
		iProduct.setDesignation("JUnit Test");
		iProduct.setCreationDate(new Date());
		iProduct.setUpdateDate(new Date());
		iProduct.setTva(iTva);

		final ProductPackaging iProductPackaging = iProduct.add(iPackaging);

		iProductDao.beginTransaction();
		iProductDao.insert(iProduct);
		iProductDao.commit();

		final Product iReadedProduct = (Product) session.get(Product.class, iProduct.getUuid());
		assertFalse(iReadedProduct == null);

		final ProductPackagingLot iProductPackagingLot = new ProductPackagingLot();
		iProductPackagingLot.parse(0, iProductPackaging, 0.0, 0.0, null);

		final ProductPackagingLotDAO iProductPackagingLotDAO = new ProductPackagingLotDAO();
		iProductPackagingLotDAO.beginTransaction();
		iProductPackagingLotDAO.insert(iProductPackagingLot);
		iProductPackagingLotDAO.commit();

		iProductPackagingLot.add(1, 10.0);
		iProductPackagingLot.add(2, 5.0);
		iProductPackagingLot.add(3, 7.0);

		iProductPackagingLotDAO.beginTransaction();
		iProductPackagingLotDAO.update(iProductPackagingLot);
		iProductPackagingLotDAO.commit();

		iProductPackagingLotDAO.beginTransaction();
		iProductPackagingLotDAO.delete(iProductPackagingLot);
		iProductPackagingLotDAO.commit();

		iReadedProduct.remove(iPackaging);
		iProductDao.beginTransaction();
		iProductDao.update(iProduct);
		iProductDao.commit();

		iProductDao.beginTransaction();
		iProductDao.delete(iReadedProduct);
		iProductDao.commit();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
