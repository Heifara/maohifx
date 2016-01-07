/**
 * 
 */
package com.maohi.software.maohifx.common.server;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
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

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		return sessionFactory;
	}

	public static void setConfigurationURL(URL url) {
		configFileUrl = url;
	}
}
