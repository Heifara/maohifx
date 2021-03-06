/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author heifara
 *
 */
public abstract class AbstractDAO<E> {

	/**
	 * Shared {@link Session} between DAO.
	 */
	protected static Session sharedSession;

	/**
	 * Set the shared {@link Session} between DAO
	 *
	 * @param aSession
	 */
	public static void setSharedSession(final Session aSession) {
		sharedSession = aSession;
	}

	/**
	 * The current {@link Session} of the DAO. Can be different from the SharedSession.
	 */
	protected Session session;

	public AbstractDAO() {
		this.setSession(sharedSession);
	}

	public void beginTransaction() {
		this.session.beginTransaction();
	}

	public void commit() {
		this.session.getTransaction().commit();
	}

	public void delete(final E aEntity) {
		this.session.delete(aEntity);
	}

	public boolean exists(final Serializable aId) {
		if (aId == null) {
			return false;
		}

		final E iElement = this.read(aId);
		if (iElement != null) {
			this.session.evict(iElement);
			return true;
		} else {
			return false;
		}
	}

	public abstract Class<E> getAnnotatedClass();

	public void insert(final E aEntity) {
		this.session.save(aEntity);
	}

	public <T> T next(final Class<T> aType, final String aAttribute) {
		return this.next(aType, aAttribute, "");
	}

	@SuppressWarnings("unchecked")
	public <T> T next(final Class<T> aType, final String aAttribute, final String aWhere) {
		final Query iQuery = this.session.createQuery(String.format("SELECT MAX(%s) as max FROM %s %s", aAttribute, this.getAnnotatedClass().getSimpleName(), aWhere));
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
		if (aId == null) {
			return null;
		}

		return (E) this.session.get(this.getAnnotatedClass(), aId);
	}

	public List<E> readAll() {
		return this.readAll(null);
	}

	@SuppressWarnings("unchecked")
	public List<E> readAll(final String aWhere) {
		final Query iQuery = this.session.createQuery(String.format("FROM %s %s", this.getAnnotatedClass().getSimpleName(), aWhere != null ? "WHERE " + aWhere : ""));
		final List<E> iElements = iQuery.list();
		return iElements;
	}

	public void replace(final E aEntity) {
		this.session.saveOrUpdate(aEntity);
	}

	public void setSession(final Session aSession) {
		this.session = aSession;
	}

	public void update(final E aEntity) {
		this.session.update(aEntity);
	}

}
