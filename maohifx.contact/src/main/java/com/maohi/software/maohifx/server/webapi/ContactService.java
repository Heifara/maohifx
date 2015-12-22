/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.contact.bean.Contact;
import com.maohi.software.maohifx.contact.dao.ContactDAO;

/**
 * @author heifara
 *
 */
@Path("contact")
public class ContactService extends AnnotatedClassService<ContactDAO, Contact> {

	public ContactService() throws InstantiationException, IllegalAccessException {
		super();
	}

	@Override
	Class<Contact> getAnnotatedClass() {
		return Contact.class;
	}

	@Override
	Class<ContactDAO> getDAOClass() {
		return ContactDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final Contact iElement) {
		return null;
	}

	@Override
	public void onInserted(final Contact iElement) {

	}

	@Override
	public void onInserting(final Contact iElement) {
		iElement.bindChildren();
	}

	@Override
	public void onSaved(final Contact iElement) {

	}

	@Override
	public void onSaving(final Contact iElement) {

	}

	@Override
	public void onUpdated(final Contact iElement) {

	}

	@Override
	public void onUpdating(final Contact iElement) {
		iElement.bindChildren();
	}

	@Override
	public List<Contact> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final Contact iElement) {
		return null;
	}

}
