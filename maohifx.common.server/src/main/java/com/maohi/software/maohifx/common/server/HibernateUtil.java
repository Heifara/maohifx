/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.service.ServiceRegistry;

/**
 * @author heifara
 *
 */
public class HibernateUtil {

	private static final Configuration configuration = new Configuration();
	private static SessionFactory sessionFactory;
	private static URL configFileUrl;

	private static SessionFactory buildSessionFactory() {
		try {
			System.out.println(System.getProperty("user.dir"));
			configuration.configure(configFileUrl);
			final ServiceRegistry iServiceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			return configuration.buildSessionFactory(iServiceRegistry);
		} catch (final Throwable aThrowable) {
			System.err.println(aThrowable.getMessage());
			throw new ExceptionInInitializerError(aThrowable);
		}
	}

	/**
	 * Return the AnnotatedClass matching aEntity
	 *
	 * @param aEntity
	 *            the entity
	 * @return the {@link Class}
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends AnnotatedClass> getAnnotatedClass(final String aEntity) {
		Class<? extends AnnotatedClass> iClass = null;
		final Iterator<PersistentClass> iIterator = HibernateUtil.getConfiguration().getClassMappings();
		while (iIterator.hasNext()) {
			final PersistentClass iPersistentClass = iIterator.next();
			if (aEntity.toLowerCase().equals(iPersistentClass.getTable().getName())) {
				iClass = iPersistentClass.getMappedClass();
				break;
			}
		}
		return iClass;
	}

	/**
	 * Return the id of the element
	 *
	 * @param aElement
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Serializable getAnnotatedClassId(final Object aElement) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Serializable iId = null;
		final Method[] iMethods = aElement.getClass().getMethods();
		for (final Method iMethod : iMethods) {
			if (iMethod.isAnnotationPresent(Id.class)) {
				iId = (Serializable) iMethod.invoke(aElement);
			}
		}
		return iId;
	}

	/**
	 * Return the {@link Class} of the Id
	 *
	 * @param aClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends Serializable> getAnnotatedClassIdType(final Class<?> aClass) {
		Class<? extends Serializable> iClassId = null;
		final Method[] iMethods = aClass.getMethods();
		for (final Method iMethod : iMethods) {
			if (iMethod.isAnnotationPresent(Id.class)) {
				iClassId = (Class<? extends Serializable>) iMethod.getReturnType();
				break;
			} else if (iMethod.isAnnotationPresent(EmbeddedId.class)) {
				iClassId = (Class<? extends Serializable>) iMethod.getReturnType();
				break;
			}
		}
		return iClassId;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
	}

	public static void setConfigurationURL(final URL url) {
		configFileUrl = url;
	}
}
