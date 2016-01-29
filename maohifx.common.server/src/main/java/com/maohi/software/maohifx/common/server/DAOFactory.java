/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heifara
 *
 */
@SuppressWarnings("rawtypes")
public class DAOFactory {

	private static DAOFactory singleton;

	public static DAOFactory getInstance() {
		if (singleton == null) {
			singleton = new DAOFactory();
		}
		return singleton;
	}

	public static AbstractDAO getInstance(final Class<?> aClass) throws InstantiationException, IllegalAccessException {
		return getInstance().get(aClass);
	}

	private final Map<Class<?>, Class<? extends AbstractDAO>> daoClasses;
	private final Map<Thread, Map<Class<?>, AbstractDAO>> threadMappings;

	public DAOFactory() {
		this.daoClasses = new HashMap<>();
		this.threadMappings = new HashMap<>();
	}

	public AbstractDAO get(final Class<?> aClass) throws InstantiationException, IllegalAccessException {
		Map<Class<?>, AbstractDAO> iAnnotatedClassMappings = this.threadMappings.get(Thread.currentThread());
		if (iAnnotatedClassMappings == null) {
			iAnnotatedClassMappings = new HashMap<>();
			this.threadMappings.put(Thread.currentThread(), iAnnotatedClassMappings);
		}

		AbstractDAO iDAO = iAnnotatedClassMappings.get(aClass);
		if (iDAO == null) {
			final Class<? extends AbstractDAO> iDAOClass = this.daoClasses.get(aClass);
			if (iDAOClass != null) {
				iDAO = iDAOClass.newInstance();
				iDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
				iAnnotatedClassMappings.put(aClass, iDAO);
			}
		}

		return iDAO;
	}

	public void set(final Class<?> aAnnotatedClass, final Class<? extends AbstractDAO<?>> aDAO) {
		this.daoClasses.put(aAnnotatedClass, aDAO);
	}
}
