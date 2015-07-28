/**
 * 
 */
package com.maohi.software.maohifx.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author heifara
 *
 */
public abstract class AbstractDAO<E> {

	private static Session session;

	public static void setSession(final Session aSession) {
		session = aSession;
	}

	public void beginTransaction() {
		AbstractDAO.session.beginTransaction();
	}

	public void commit() {
		AbstractDAO.session.getTransaction().commit();
	}

	public void delete(final E aEntity) {
		AbstractDAO.session.delete(aEntity);
	}

	public abstract Class<E> getAnnotatedClass();

	public void insert(final E aEntity) {
		AbstractDAO.session.save(aEntity);
	}

	@SuppressWarnings("unchecked")
	public <T> T next(final Class<T> aType, final String aAttribute) {
		final Query iQuery = AbstractDAO.session.createQuery(String.format("SELECT MAX(%s) as max FROM %s", aAttribute, this.getAnnotatedClass().getSimpleName()));
		final T iNext = (T) iQuery.uniqueResult();
		if (aType.isAssignableFrom(Integer.class)) {
			return (T) (iNext == null ? new Integer(0) : new Integer((Integer) iNext + 1));
		} else if (aType.isAssignableFrom(Double.class)) {
			return (T) (iNext == null ? new Double(0) : new Double((Double) iNext + 1.0));
		} else {
			return iNext;
		}
	}

	@SuppressWarnings("unchecked")
	public E read(final Serializable aId) {
		return (E) AbstractDAO.session.get(this.getAnnotatedClass(), aId);
	}

	@SuppressWarnings("unchecked")
	public List<E> readAll() {
		final Query iQuery = AbstractDAO.session.createQuery("FROM " + this.getAnnotatedClass().getSimpleName());
		final List<E> iElements = iQuery.list();
		return iElements;
	}

	public void update(final E aEntity) {
		AbstractDAO.session.update(aEntity);
	}

}