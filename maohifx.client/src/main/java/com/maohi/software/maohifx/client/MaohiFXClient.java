/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class MaohiFXClient extends Application {

	private final ExtFXMLLoader loader;
	private BorderPane mainPane;
	private TabPane tabpane;

	public MaohiFXClient() {
		this.loader = new ExtFXMLLoader();
		this.loader.setLocation(this.getClass().getResource("MaohiFXClient.fxml"));
	}

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(final Stage aStage) {
		try {
			this.loader.getNamespace().put("$stage", aStage);

			this.mainPane = this.loader.load();
			this.tabpane = (TabPane) this.mainPane.getCenter();
			this.loader.getNamespace().put("$mainPane", mainPane);
			this.loader.getNamespace().put("$tabpane", tabpane);

			final Scene iScene = new Scene(this.mainPane);
			this.loader.getNamespace().put("$scene", iScene);

			aStage.setScene(iScene);
			aStage.show();

			this.loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab").load(tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoices");

		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(4);
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}
