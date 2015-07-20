/**
 * 
 */
package com.maohi.software.maohifx.server.webapi;

import static org.junit.Assert.*;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
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
public class PostTest {

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
	public void shouldOkFromForm() throws Exception {
		Form iForm = new Form();
		iForm.param("login", "Metallica");
		iForm.param("password", "Fade To Black");
		Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx-server/webapi/post").request().post(Entity.entity(iForm, MediaType.APPLICATION_FORM_URLENCODED));
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

	@Test
	public void shouldOkFromJSON() throws Exception {
		Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx-server/webapi/post").request().post(Entity.entity(new String("Hi"), MediaType.APPLICATION_JSON));
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
