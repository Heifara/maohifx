/**
 *
 */
package com.maohi.software.maohifx.contact.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.contact.bean.Contact;

/**
 * @author heifara
 *
 */
public class ContactDAO extends AbstractDAO<Contact> {

	@Override
	public Class<Contact> getAnnotatedClass() {
		return Contact.class;
	}

}
