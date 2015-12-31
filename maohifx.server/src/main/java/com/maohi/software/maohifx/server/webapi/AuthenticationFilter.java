package com.maohi.software.maohifx.server.webapi;

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.maohi.software.maohifx.common.Profile;

/**
 * @author heifara
 *
 */
@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter {

	/**
	 * @author heifara
	 *
	 */
	public class AuthenticationSecurityContext implements SecurityContext {

		Profile profile;

		public AuthenticationSecurityContext(final Profile aProfile) {
			this.profile = aProfile;
		}

		@Override
		public String getAuthenticationScheme() {
			return SecurityContext.FORM_AUTH;
		}

		@Override
		public Principal getUserPrincipal() {
			return this.profile;
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public boolean isUserInRole(final String aRole) {
			if (this.profile.getRole().equals(aRole)) {
				return true;
			}

			return false;
		}

	}

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();
	private static Map<String, Profile> registeredToken = new HashMap<>();

	public static void put(final String aToken, final Profile aProfile) {
		registeredToken.put(aToken, aProfile);
	}

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
			final String endcodedTokenAndRole = iAuthorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode username and password
			final String tokenAndRole = new String(Base64.decode(endcodedTokenAndRole.getBytes()));

			// Split username and password tokens
			final StringTokenizer tokenizer = new StringTokenizer(tokenAndRole, ":");

			// Check Token if not empty
			final String iToken = tokenizer.nextToken();
			if (iToken.isEmpty()) {
				iRequestContext.abortWith(ACCESS_DENIED);
				return;
			}

			// Check if Token has not expired
			final Profile iProfile = registeredToken.get(iToken);
			if (iProfile == null) {
				iRequestContext.abortWith(ACCESS_DENIED);
				return;
			}
			iRequestContext.setSecurityContext(new AuthenticationSecurityContext(iProfile));

			// Check Role
			final String iRole = tokenizer.nextToken();
			if (iMethod.isAnnotationPresent(RolesAllowed.class)) {
				final RolesAllowed rolesAnnotation = iMethod.getAnnotation(RolesAllowed.class);
				final Set<String> iAuthorizedRolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
				if (!iAuthorizedRolesSet.contains(iRole)) {
					iRequestContext.abortWith(ACCESS_DENIED);
					return;
				}

			}
		}
	}
}