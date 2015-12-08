/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author heifara
 *
 */
public class InvoiceServiceTest {

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
	public void testName() throws Exception {
		final Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx.server/webapi/invoice").queryParam("uuid", "5ebb4cc3-cc72-4c31-b647-ee6d734ea641").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

}
