/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.server.ResourceConfig;

import com.maohi.software.maohifx.common.server.DAOFactory;
import com.maohi.software.maohifx.common.server.PDFBuilderFactory;
import com.maohi.software.maohifx.contact.bean.Contact;
import com.maohi.software.maohifx.contact.bean.Salesman;
import com.maohi.software.maohifx.contact.dao.ContactDAO;
import com.maohi.software.maohifx.contact.dao.SalesmanDAO;
import com.maohi.software.maohifx.invoice.InvoicePDFBuilder;
import com.maohi.software.maohifx.invoice.bean.Invoice;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.dao.InvoiceDAO;
import com.maohi.software.maohifx.invoice.dao.InvoiceLineDAO;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.bean.ProductPackaging;
import com.maohi.software.maohifx.product.bean.ProductPackagingMovement;
import com.maohi.software.maohifx.product.dao.ProductDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingDAO;
import com.maohi.software.maohifx.product.dao.ProductPackagingMovementDAO;

/**
 * @author heifara
 *
 */
public class MyApplication extends ResourceConfig {

	private static List<Runnable> runLater = new ArrayList<>();

	public static void runLater(final Runnable aRunnable) {
		runLater.add(aRunnable);
	}

	public MyApplication() {
		System.setProperty("java.io.tmpdir", "E:\\Temp");

		System.out.println("Loading DAOFactory");
		DAOFactory.getInstance().set(Invoice.class, InvoiceDAO.class);
		DAOFactory.getInstance().set(InvoiceLine.class, InvoiceLineDAO.class);
		DAOFactory.getInstance().set(Product.class, ProductDAO.class);
		DAOFactory.getInstance().set(Contact.class, ContactDAO.class);
		DAOFactory.getInstance().set(Salesman.class, SalesmanDAO.class);
		DAOFactory.getInstance().set(ProductPackaging.class, ProductPackagingDAO.class);
		DAOFactory.getInstance().set(ProductPackagingMovement.class, ProductPackagingMovementDAO.class);

		System.out.println("Loading PDFBuilderFactory");
		PDFBuilderFactory.getInstance().setBuilder(Invoice.class.getSimpleName(), InvoicePDFBuilder.class);

		this.fireRunLater();
	}

	private void fireRunLater() {
		for (final Runnable iRunnable : runLater) {
			final Thread iThread = new Thread(iRunnable);
			iThread.start();
		}
	}
}
