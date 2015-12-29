/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
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

	@Test
	public void shouldOkFromJSON() throws Exception {
		final HttpAuthenticationFeature iAuthentification = HttpAuthenticationFeature.basic("username", "password");

		final Response iResponse = ClientBuilder.newClient().register(iAuthentification).target("http://localhost:8080/maohifx.server/webapi/get").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

	@Test
	public void shouldOkFromJSONQuery() throws Exception {
		final HttpAuthenticationFeature iAuthentification = HttpAuthenticationFeature.basic("username", "password");

		final Response iResponse = ClientBuilder.newClient().register(iAuthentification).target("http://localhost:8080/maohifx.server/webapi/get").queryParam("id", "myId").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
		assertTrue("myId".equals(iResponse.readEntity(String.class)));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAuthorized() throws Exception {
		final HttpAuthenticationFeature iAuthentification = HttpAuthenticationFeature.basic("authorized", "authorized");

		final Response iResponse = ClientBuilder.newClient().register(iAuthentification).target("http://localhost:8080/maohifx.server/webapi/get/authorized").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

	@Test
	public void testForbidden() throws Exception {
		final Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx.server/webapi/get/forbidden").request().get();
		assertTrue(Status.FORBIDDEN.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

	@Test
	public void testUnauthorized() throws Exception {
		final HttpAuthenticationFeature iAuthentification = HttpAuthenticationFeature.basic("unauthorized", "unautho");

		final Response iResponse = ClientBuilder.newClient().register(iAuthentification).target("http://localhost:8080/maohifx.server/webapi/get/authorized").request().get();
		assertTrue(Status.UNAUTHORIZED.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

}
