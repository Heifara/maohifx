/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.contact.bean.Supplier;
import com.maohi.software.maohifx.contact.dao.ContactDAO;
import com.maohi.software.maohifx.contact.dao.SupplierDAO;

/**
 * @author heifara
 *
 */
@Path("supplier")
public class SupplierService extends AnnotatedClassService<SupplierDAO, Supplier> {

	public SupplierService() throws InstantiationException, IllegalAccessException {
		super();

	}

	@Override
	Class<Supplier> getAnnotatedClass() {
		return Supplier.class;
	}

	@Override
	Class<SupplierDAO> getDAOClass() {
		return SupplierDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final Supplier iElement) {
		return null;
	}

	@Override
	public void onInserted(final Supplier iElement) {

	}

	@Override
	public void onInserting(final Supplier iElement) {
		final ContactDAO iDao = new ContactDAO();
		iDao.insert(iElement.getContact());
	}

	@Override
	public void onSaved(final Supplier iElement) {

	}

	@Override
	public void onSaving(final Supplier iElement) {

	}

	@Override
	public void onUpdated(final Supplier iElement) {

	}

	@Override
	public void onUpdating(final Supplier iElement) {

	}

	@Override
	public List<Supplier> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final Supplier iElement) {
		return null;
	}
}
