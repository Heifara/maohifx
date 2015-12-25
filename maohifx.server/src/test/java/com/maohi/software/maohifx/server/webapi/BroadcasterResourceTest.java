/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author heifara
 *
 */
public class BroadcasterResourceTest {

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
	public void testBroadcasting() throws Exception {
		final Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		final WebTarget target = client.target("http://localhost:9998/events");

		final EventInput eventInput = target.request().get(EventInput.class);
		while (!eventInput.isClosed()) {
			final InboundEvent inboundEvent = eventInput.read();
			if (inboundEvent == null) {
				// connection has been closed
				break;
			}
			System.out.println(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
		}
	}

}
