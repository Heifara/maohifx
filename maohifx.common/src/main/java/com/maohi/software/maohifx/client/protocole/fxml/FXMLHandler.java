/**
 *
 */
package com.maohi.software.maohifx.client.protocole.fxml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @author heifara
 *
 */
public class FXMLHandler extends URLStreamHandler {

	private static class FXMLURLConnection extends URLConnection {

		public FXMLURLConnection(final URL url) {
			super(url);
		}

		@Override
		public void connect() throws IOException {
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return super.getInputStream();
		}
	}

	@Override
	protected URLConnection openConnection(final URL url) throws IOException {
		return new FXMLURLConnection(url);
	}

}
