/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @see https://jersey.java.net/documentation/latest/sse.html
 *
 * @author heifara
 *
 */
public class SseResourceTest {

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

	/**
	 * @throws Exception
	 * @see https://jersey.java.net/documentation/latest/sse.html
	 */
	@Test
	public void testAsyncBoundToAnyEvent() throws Exception {
		final Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		final WebTarget target = client.target("http://localhost:8080/maohifx.server/webapi/events");
		final EventSource eventSource = new EventSource(target) {
			@Override
			public void onEvent(final InboundEvent inboundEvent) {
				if ("message-to-client".equals(inboundEvent.getName())) {
					System.out.println("event: " + inboundEvent.getName());
					System.out.println("data:" + inboundEvent.readData(String.class));
				}
			}
		};
		eventSource.close();
	}

	/**
	 * @throws Exception
	 * @see https://jersey.java.net/documentation/latest/sse.html
	 */
	@Test
	public void testAsyncEvent() throws Exception {
		final Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		final WebTarget target = client.target("http://localhost:8080/maohifx.server/webapi/events");
		final EventSource eventSource = EventSource.target(target).build();
		final EventListener listener = new EventListener() {
			@Override
			public void onEvent(final InboundEvent inboundEvent) {
				System.out.println("event: " + inboundEvent.getName());
				System.out.println("data:" + inboundEvent.readData(String.class));
			}
		};
		eventSource.register(listener, "message-to-client");
		eventSource.open();
		eventSource.close();
	}

	@Test
	public void testEvent() throws Exception {
		final Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
		final WebTarget target = client.target("http://localhost:8080/maohifx.server/webapi/events");

		final EventInput eventInput = target.request().get(EventInput.class);
		while (!eventInput.isClosed()) {
			final InboundEvent inboundEvent = eventInput.read();
			if (inboundEvent == null) {
				// connection has been closed
				break;
			}
			System.out.println("event: " + inboundEvent.getName());
			System.out.println("data:" + inboundEvent.readData(String.class));
		}
	}

}
