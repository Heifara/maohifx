/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.contact.bean.Customer;
import com.maohi.software.maohifx.contact.dao.ContactDAO;
import com.maohi.software.maohifx.contact.dao.CustomerDAO;

/**
 * @author heifara
 *
 */
@Path("customer")
public class CustomerService extends AnnotatedClassService<CustomerDAO, Customer> {

	private boolean insertContact;

	public CustomerService() throws InstantiationException, IllegalAccessException {
		super();
		this.insertContact = false;
	}

	@Override
	Class<Customer> getAnnotatedClass() {
		return Customer.class;
	}

	@Override
	Class<CustomerDAO> getDAOClass() {
		return CustomerDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final Customer iElement) {
		return null;
	}

	@Override
	public void onInserted(final Customer iElement) {

	}

	@Override
	public void onInserting(final Customer iElement) {
		if (this.insertContact) {
			new ContactDAO().insert(iElement.getContact());
		}

		iElement.getContact().setCreationDate(new Date());
		iElement.getContact().setUpdateDate(new Date());
	}

	@Override
	public void onSaved(final Customer iElement) {

	}

	@Override
	public void onSaving(final Customer iElement) {
		this.insertContact = iElement.getUuid() == null ? true : false;

	}

	@Override
	public void onUpdated(final Customer iElement) {

	}

	@Override
	public void onUpdating(final Customer iElement) {
		iElement.getContact().setUpdateDate(new Date());
	}

	@Override
	public List<Customer> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final Customer iElement) {
		return null;
	}
}
