/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.jaxb2.Configuration.Authentication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
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

	@FXML
	private CheckBox rememberMe;

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
			if (this.rememberMe.isSelected()) {
				final Configuration iConfiguration = this.view.getController().getConfiguration();
				final Authentication iAuthentication = iConfiguration.getAuthentication() != null ? iConfiguration.getAuthentication() : new Authentication();
				iAuthentication.setUsername(this.username.getText());
				iAuthentication.setPassword(this.password.getText());
				iConfiguration.setAuthentication(iAuthentication);
				this.view.getController().getModel().save(iConfiguration);
			}

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
