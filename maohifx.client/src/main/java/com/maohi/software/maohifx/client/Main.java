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
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class Main extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	@Option(name = "-config", usage = "path to the config file")
	private String config;

	@Override
	public void start(final Stage aStage) throws Exception {
		this.config = "./config.xml";

		try {
			final CmdLineParser iParser = new CmdLineParser(this);
			iParser.parseArgument(this.getParameters().getRaw());

			final boolean isDebug = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
			if (isDebug) {
				System.out.println("Push Enter to start ");
				final BufferedReader iReader = new BufferedReader(new InputStreamReader(System.in));
				iReader.readLine();
			}

			final MaohiFXController iController = new MaohiFXController();
			iController.setView(new MaohiFXView(iController, aStage));
			iController.setModel(new MaohiFXModel(this.config));
			iController.show();

		} catch (final IOException | CmdLineException aException) {
			aException.printStackTrace();
		}
	}
}
