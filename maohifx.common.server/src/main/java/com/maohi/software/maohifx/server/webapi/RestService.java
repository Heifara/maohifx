/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.URIResolver;

/**
 * @author heifara
 *
 */
public class RestService {

	@Context
	URIResolver uriResolver;

	@Context
	HttpServletRequest httpServletRequest;

	@Context
	HttpServletResponse httpServletResponse;

	@Context
	ServletContext servletContext;

	@Context
	UriInfo uriInfo;

	@Context
	SecurityContext securityContext;

	private File baseDir;

	public File getBaseDir() {
		if (this.baseDir == null) {
			this.baseDir = new File("C:\\Users\\heifara.INFOEL\\workspace\\maohifx\\maohifx.server\\src\\main\\webapp");
		}
		return this.baseDir;
	}

	public String getLocalContextUri() {
		String iLocalContextUri = this.httpServletRequest.getScheme();
		iLocalContextUri += "://" + this.httpServletRequest.getServerName();
		iLocalContextUri += (("http".equals(this.httpServletRequest.getScheme()) && (this.httpServletRequest.getServerPort() == 80)) || ("https".equals(this.httpServletRequest.getScheme()) && (this.httpServletRequest.getServerPort() == 443)) ? "" : ":" + this.httpServletRequest.getServerPort());
		iLocalContextUri += this.httpServletRequest.getContextPath();
		return iLocalContextUri;
	}

	public String getLocalUri() {
		return this.uriInfo.getBaseUri().toString();
	}

	public String getWorkingDir() {
		return System.getProperty("user.dir");
	}

	public String queryParametersToJSON() {
		final StringBuilder iJSON = new StringBuilder();
		int iIndexOf = -1;
		final Iterator<String> iIterator = this.uriInfo.getQueryParameters().keySet().iterator();
		while (iIterator.hasNext()) {
			final String iKey = iIterator.next();
			iIndexOf++;

			if (iIndexOf == 0) {
				iJSON.append("{");
			}

			iJSON.append("\"");
			iJSON.append(iKey);
			iJSON.append("\":");
			iJSON.append("\"");
			final List<String> iValue = this.uriInfo.getQueryParameters().get(iKey);
			iJSON.append(iValue.get(0));
			iJSON.append("\"");

			if (iIndexOf == (this.uriInfo.getQueryParameters().size() - 1)) {
				iJSON.append("}");
			} else {
				iJSON.append(",");
			}
		}

		return iJSON.toString();
	}

}
