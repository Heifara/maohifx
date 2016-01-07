/**
 *
 */
package com.maohi.software.maohifx.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class BrowserTest extends Application {

	public static void main(final String[] args) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {
		final Browser iBrowser = new Browser();

		final TextField iUrlTextField = new TextField("http://localhost:8080/");
		iUrlTextField.setTooltip(new Tooltip("Saisir une url"));
		iUrlTextField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent aEvent) {
				iBrowser.load(iUrlTextField.getText());
			}
		});

		final BorderPane iPane = new BorderPane();
		iPane.setTop(iUrlTextField);
		iPane.setCenter(iBrowser);
		aPrimaryStage.setScene(new Scene(iPane, 800, 600));
		aPrimaryStage.show();
	}

}
