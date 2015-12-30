/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * @author heifara
 *
 */
public class LoginPane extends BorderPane {

	private final MaohiFXView view;

	private FXMLLoader loader;

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	public LoginPane(final MaohiFXView aView) {
		this.view = aView;

		try {
			this.loader = new FXMLLoader();
			this.loader.setBuilderFactory(this.view.getBuilderFactory());
			this.loader.setLocation(this.getClass().getResource("LoginPane.fxml"));
			this.loader.setRoot(this);
			this.loader.setController(this);

			this.loader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	@FXML
	public void connect() {
		if (this.view.getController().connect(this.username.getText(), this.password.getText())) {
			final Window iWindow = this.getScene().getWindow();
			if (iWindow instanceof PopOver) {
				final PopOver iPopOver = (PopOver) iWindow;
				iPopOver.hide();
			} else if (iWindow instanceof Stage) {
				final Stage iStage = (Stage) iWindow;
				iStage.close();
			} else {
				iWindow.hide();
			}
		}
	}
}
