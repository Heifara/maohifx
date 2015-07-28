/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import javafx.application.Application;

/**
 * @author heifara
 *
 */
public class Main {

	public static void main(final String[] args) {
		new Main().doMain(args);
	}

	@Option(name = "-url", usage = "the very 1st URL to execute")
	private String url;

	private void doMain(final String[] aArgs) {
		try {
			final CmdLineParser iParser = new CmdLineParser(this);
			iParser.parseArgument(aArgs);

			final boolean isDebug = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
			if (isDebug) {
				System.out.println("Push Enter to start ");
				final BufferedReader iReader = new BufferedReader(new InputStreamReader(System.in));
				iReader.readLine();
			}

			Application.launch(MaohiFXClient.class, this.url);

		} catch (final CmdLineException aException) {
			aException.printStackTrace();
		} catch (final IOException aException) {
			aException.printStackTrace();
		}
	}

}
