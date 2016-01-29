/**
 *
 */
package com.maohi.software.maohifx.common;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author heifara
 *
 */
public class StringsTest {

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
	public void testToCamelCase() throws Exception {
		assertTrue("HelloWorldWelcomeHome".equals(Strings.toCamelCase("HELLO_WORLD_WELCOME_HOME")));
	}

	@Test
	public void testToUnderscore() throws Exception {
		assertTrue("hello_world_welcom_home".equals(Strings.toUnderscore("HelloWorldWelcomHome")));
	}

}
