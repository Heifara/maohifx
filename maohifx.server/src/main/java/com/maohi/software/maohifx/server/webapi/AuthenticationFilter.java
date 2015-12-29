package com.maohi.software.maohifx.server.webapi;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

/**
 * This filter verify the access permissions for a user based on username and passowrd provided in request
 */
@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(final ContainerRequestContext iRequestContext) {
		final Method iMethod = this.resourceInfo.getResourceMethod();
		// Access allowed for all
		if (!iMethod.isAnnotationPresent(PermitAll.class)) {
			// Access denied for all
			if (iMethod.isAnnotationPresent(DenyAll.class)) {
				iRequestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			// Get request headers
			final MultivaluedMap<String, String> iHeaders = iRequestContext.getHeaders();

			// Fetch authorization header
			final List<String> iAuthorization = iHeaders.get(AUTHORIZATION_PROPERTY);

			// If no authorization information present; block access
			if ((iAuthorization == null) || iAuthorization.isEmpty()) {
				iRequestContext.abortWith(ACCESS_DENIED);
				return;
			}

			// Get encoded username and password
			final String encodedUserPassword = iAuthorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode username and password
			final String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
			;

			// Split username and password tokens
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			// Verify user access
			if (iMethod.isAnnotationPresent(RolesAllowed.class)) {
				final RolesAllowed rolesAnnotation = iMethod.getAnnotation(RolesAllowed.class);
				final Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				// Is user valid?
				if (!this.isUserAllowed(username, password, rolesSet)) {
					iRequestContext.abortWith(ACCESS_DENIED);
					return;
				}
			}
		}
	}

	private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
		boolean isAllowed = false;

		// Step 1. Fetch password from database and match with password in argument
		// If both match then get the defined role for user from database and continue; else return isAllowed [false]
		// Access the database and do this part yourself
		// String userRole = userMgr.getUserRole(username);

		if (username.equals("authorized") && password.equals("authorized")) {
			final String userRole = "user";

			// Step 2. Verify user role
			if (rolesSet.contains(userRole)) {
				isAllowed = true;
			}
		}
		return isAllowed;
	}
}