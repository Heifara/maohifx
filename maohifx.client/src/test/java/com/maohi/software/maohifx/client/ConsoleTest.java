/**
 *
 */
package com.maohi.software.maohifx.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ConsoleTest extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {
		final Console iConsole = new Console();

		System.setOut(iConsole.getPrintStream());
		System.setErr(iConsole.getPrintStream());

		aPrimaryStage.setScene(new Scene(iConsole, 800, 600));
		aPrimaryStage.show();

		for (int iI = 0; iI < 1000; iI++) {
			System.out.println("Hello World");
			System.err.println("Im an error");
		}
	}

}
