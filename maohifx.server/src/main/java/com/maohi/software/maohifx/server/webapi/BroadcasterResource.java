/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * This is a 2nd test in jersey push
 *
 * @author heifara
 *
 */
@Singleton
@Path("broadcast")
public class BroadcasterResource {

	private final SseBroadcaster broadcaster = new SseBroadcaster();

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String broadcastMessage(final String message) {
		final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		final OutboundEvent event = eventBuilder.name("message").mediaType(MediaType.TEXT_PLAIN_TYPE).data(String.class, message).build();

		this.broadcaster.broadcast(event);

		return "Message '" + message + "' has been broadcast.";
	}

	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput listenToBroadcast() {
		final EventOutput eventOutput = new EventOutput();
		this.broadcaster.add(eventOutput);
		return eventOutput;
	}
}
