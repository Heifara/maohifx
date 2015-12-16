/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * @author heifara
 *
 */
public class RestService {

	@Context
	HttpServletRequest httpServletRequest;

	@Context
	ServletContext servletContext;

	public String getLocalContextUri() {
		String iLocalContextUri = this.httpServletRequest.getScheme();
		iLocalContextUri += "://" + this.httpServletRequest.getServerName();
		iLocalContextUri += (("http".equals(this.httpServletRequest.getScheme()) && (this.httpServletRequest.getServerPort() == 80)) || ("https".equals(this.httpServletRequest.getScheme()) && (this.httpServletRequest.getServerPort() == 443)) ? "" : ":" + this.httpServletRequest.getServerPort());
		iLocalContextUri += this.httpServletRequest.getContextPath();
		return iLocalContextUri;
	}

}
