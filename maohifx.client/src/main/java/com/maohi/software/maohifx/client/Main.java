/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URL;

import com.maohi.software.maohifx.client.protocole.fxml.URLStreamHandlerFactoryImpl;

import javafx.application.Application;

/**
 * @author heifara
 *
 */
public class Main {

	public static void main(final String[] args) {
		URL.setURLStreamHandlerFactory(new URLStreamHandlerFactoryImpl());

		new Main().doMain(args);
	}

	private void doMain(final String[] aArgs) {
		try {
			final boolean isDebug = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
			if (isDebug) {
				System.out.println("Push Enter to start ");
				final BufferedReader iReader = new BufferedReader(new InputStreamReader(System.in));
				iReader.readLine();
			}

			Application.launch(MaohiFXClient.class, aArgs);

		} catch (final IOException aException) {
			aException.printStackTrace();
		}
	}

}
