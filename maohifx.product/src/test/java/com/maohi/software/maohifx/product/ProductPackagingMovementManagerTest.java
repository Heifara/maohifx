/**
 *
 */
package com.maohi.software.maohifx.product;

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

import com.maohi.software.maohifx.common.server.AbstractDAO;
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
import com.maohi.software.maohifx.product.dao.ProductDAO;

/**
 * @author heifara
 *
 */
public class ProductPackagingMovementManagerTest {

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
		AbstractDAO.setSharedSession(session);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private final String productUuid = UUID.randomUUID().toString();
	private final String packagingCode = "UNT";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Tva iTva = (Tva) session.get(Tva.class, 1);
		if (iTva == null) {
			iTva = new Tva(1, new Date(), new Date(), "JUnit Test", 5.0);
			session.beginTransaction();
			session.saveOrUpdate(iTva);
			session.getTransaction().commit();
		}

		Packaging iPackaging = (Packaging) session.get(Packaging.class, this.packagingCode);
		if (iPackaging == null) {
			iPackaging = new Packaging(this.packagingCode);
			iPackaging.setCreationDate(new Date());
			iPackaging.setUpdateDate(new Date());
			iPackaging.setProductPackagings(new HashSet<>());
			session.beginTransaction();
			session.saveOrUpdate(iPackaging);
			session.getTransaction().commit();
		}

		final Product iProduct = new Product();
		iProduct.setUuid(this.productUuid);
		iProduct.setDesignation("JUnit Test");
		iProduct.setCreationDate(new Date());
		iProduct.setUpdateDate(new Date());
		iProduct.setTva(iTva);
		iProduct.add(iPackaging).add(0.0, 0.0, null, 0);
		iProduct.getProductPackaging(this.packagingCode).add(0.0, 0.0, null, 10);

		final ProductDAO iProductDao = new ProductDAO();
		iProductDao.beginTransaction();
		iProductDao.insert(iProduct);
		iProductDao.commit();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		final ProductDAO iProductDao = new ProductDAO();
		final Product iProduct = iProductDao.read(this.productUuid);
		session.refresh(iProduct);
		assertFalse(iProduct == null);

		iProductDao.beginTransaction();
		iProductDao.delete(iProduct);
		iProductDao.commit();
	}

	@Test
	public void testEntry() throws Exception {
		for (int iI = 0; iI < 1000; iI++) {
			ProductPackagingMovementManager.entry(this.productUuid, this.packagingCode, 1.0);
		}
		while (ProductPackagingMovementManager.isRunning()) {
			Thread.sleep(10);
		}
	}

	@Test
	public void testEntryOut() throws Exception {
		for (int iI = 0; iI < 1000; iI++) {
			ProductPackagingMovementManager.entry(this.productUuid, this.packagingCode, 1.0);
			ProductPackagingMovementManager.out(this.productUuid, this.packagingCode, 1.0);
		}
		while (ProductPackagingMovementManager.isRunning()) {
			Thread.sleep(10);
		}
	}

	@Test
	public void testOut() throws Exception {
		for (int iI = 0; iI < 1000; iI++) {
			ProductPackagingMovementManager.out(this.productUuid, this.packagingCode, 1.0);
		}
		while (ProductPackagingMovementManager.isRunning()) {
			Thread.sleep(10);
		}
	}

}
