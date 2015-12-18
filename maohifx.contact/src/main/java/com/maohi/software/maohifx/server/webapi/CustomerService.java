/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.contact.bean.Supplier;
import com.maohi.software.maohifx.contact.dao.ContactDAO;
import com.maohi.software.maohifx.contact.dao.SupplierDAO;

/**
 * @author heifara
 *
 */
@Path("customer")
public class CustomerService extends AnnotatedClassService<SupplierDAO, Supplier> {

	public CustomerService() throws InstantiationException, IllegalAccessException {
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
}
