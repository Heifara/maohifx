/**
 *
 */
package com.maohi.software.maohifx.client.protocole.fxml;

import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author heifara
 *
 */
public class FXMLHandlerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {

			@Override
			public URLStreamHandler createURLStreamHandler(final String protocol) {
				return new FXMLHandler();
			}
		});
		final URL iUrl = new URL("fxml://localhost:8080/maohifx.server/webapi/invoice/");
		System.out.println(iUrl.getProtocol());
		System.out.println(iUrl.getHost());
		System.out.println(iUrl.getPort());
		System.out.println(iUrl.getPath());
		System.out.println(iUrl);
	}

}
