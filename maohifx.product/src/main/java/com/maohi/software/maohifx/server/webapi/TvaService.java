/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.product.bean.Tva;
import com.maohi.software.maohifx.product.dao.TvaDAO;

/**
 * @author heifara
 *
 */
@Path("tva")
public class TvaService extends AnnotatedClassService<TvaDAO, Tva> {

	public TvaService() throws InstantiationException, IllegalAccessException {
		super();
	}

	@Override
	Class<Tva> getAnnotatedClass() {
		return Tva.class;
	}

	@Override
	Class<TvaDAO> getDAOClass() {
		return TvaDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final Tva iElement) {
		return null;
	}

	@Override
	public void onInserted(final Tva iElement) {

	}

	@Override
	public void onInserting(final Tva iElement) {

	}

	@Override
	public void onSaved(final Tva iElement) {

	}

	@Override
	public void onSaving(final Tva iElement) {

	}

	@Override
	public void onUpdated(final Tva iElement) {

	}

	@Override
	public void onUpdating(final Tva iElement) {

	}

	@Override
	public List<Tva> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final Tva iElement) {
		return null;
	}

}
