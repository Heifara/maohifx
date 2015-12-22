/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.invoice.bean.PaymentMode;
import com.maohi.software.maohifx.invoice.dao.PaymentModeDAO;

/**
 * @author heifara
 *
 */
@Path("paymentMode")
public class PaymentModeService extends AnnotatedClassService<PaymentModeDAO, PaymentMode> {

	public PaymentModeService() throws InstantiationException, IllegalAccessException {
		super();
	}

	@Override
	Class<PaymentMode> getAnnotatedClass() {
		return PaymentMode.class;
	}

	@Override
	Class<PaymentModeDAO> getDAOClass() {
		return PaymentModeDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final PaymentMode iElement) {
		return null;
	}

	public void insertPaymentMode(final int aId, final String aLabel) {
	}

	@Override
	public void onInserted(final PaymentMode iElement) {
	}

	@Override
	public void onInserting(final PaymentMode iElement) {
	}

	@Override
	public void onSaved(final PaymentMode iElement) {
	}

	@Override
	public void onSaving(final PaymentMode iElement) {
	}

	@Override
	public void onUpdated(final PaymentMode iElement) {
	}

	@Override
	public void onUpdating(final PaymentMode iElement) {
	}

	@Override
	public List<PaymentMode> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final PaymentMode iElement) {
		return null;
	}

}
