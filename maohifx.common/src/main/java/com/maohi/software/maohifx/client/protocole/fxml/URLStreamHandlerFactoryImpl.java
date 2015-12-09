/**
 *
 */
package com.maohi.software.maohifx.client.protocole.fxml;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * @author heifara
 *
 */
public class URLStreamHandlerFactoryImpl implements URLStreamHandlerFactory {

	@Override
	public URLStreamHandler createURLStreamHandler(final String aProtocol) {
		if (aProtocol.equals("fxml")) {
			return new FXMLHandler();
		} else {
			return null;
		}
	}

}
