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
public class GetTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void shouldOkFromJSON() throws Exception {
		Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx.server/webapi/get").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

	@Test
	public void shouldOkFromJSONQuery() throws Exception {
		Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx.server/webapi/get").queryParam("id", "myId").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
		assertTrue("myId".equals(iResponse.readEntity(String.class)));
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

}
