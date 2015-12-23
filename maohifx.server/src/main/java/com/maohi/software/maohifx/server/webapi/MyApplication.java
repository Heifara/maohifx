/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.Date;

import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.common.server.HibernateUtil;
import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.bean.Tva;
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;
import com.maohi.software.maohifx.invoice.dao.TvaDAO;

/**
 * @author heifara
 *
 */
public class MyApplication extends ResourceConfig {

	public MyApplication() {
		System.setProperty("java.io.tmpdir", "C:\\temp");

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
}
