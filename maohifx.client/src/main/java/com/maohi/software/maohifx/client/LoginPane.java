/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

/**
 * @author heifara
 *
 */
public class LoginPane extends BorderPane {

	private final MaohiFXClient controller;
	private final Window window;

	private FXMLLoader loader;

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	public LoginPane(final MaohiFXClient aController, final Window aWindow) {
		this.controller = aController;
		this.window = aWindow;

		try {
			this.loader = new FXMLLoader();
			this.loader.setBuilderFactory(aController.getBuilderFactory());
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
		if (this.controller.connect(this.username.getText(), this.password.getText())) {
			this.window.hide();
		}
	}
}
