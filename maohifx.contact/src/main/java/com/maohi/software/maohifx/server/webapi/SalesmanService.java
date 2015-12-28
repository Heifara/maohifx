/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Path;

import com.maohi.software.maohifx.contact.bean.Salesman;
import com.maohi.software.maohifx.contact.dao.ContactDAO;
import com.maohi.software.maohifx.contact.dao.SalesmanDAO;

/**
 * @author heifara
 *
 */
@Path("salesman")
public class SalesmanService extends AnnotatedClassService<SalesmanDAO, Salesman> {

	private boolean insertContact;

	public SalesmanService() throws InstantiationException, IllegalAccessException {
		super();
		this.insertContact = false;
	}

	@Override
	Class<Salesman> getAnnotatedClass() {
		return Salesman.class;
	}

	@Override
	Class<SalesmanDAO> getDAOClass() {
		return SalesmanDAO.class;
	}

	@Override
	protected String getJaxbPackage() {
		return null;
	}

	@Override
	protected InputStream getXslInputStream(final Salesman iElement) {
		return null;
	}

	@Override
	public void onInserted(final Salesman iElement) {

	}

	@Override
	public void onInserting(final Salesman iElement) {
		if (this.insertContact) {
			new ContactDAO().insert(iElement.getContact());
		}

		iElement.getContact().setCreationDate(new Date());
		iElement.getContact().setUpdateDate(new Date());
	}

	@Override
	public void onSaved(final Salesman iElement) {

	}

	@Override
	public void onSaving(final Salesman iElement) {
		this.insertContact = iElement.getUuid() == null ? true : false;

	}

	@Override
	public void onUpdated(final Salesman iElement) {

	}

	@Override
	public void onUpdating(final Salesman iElement) {
		iElement.getContact().setUpdateDate(new Date());
	}

	@Override
	public List<Salesman> search(final String aPattern) {
		return this.dao.readAll();
	}

	@Override
	protected Object toJaxb(final Salesman iElement) {
		return null;
	}
}
