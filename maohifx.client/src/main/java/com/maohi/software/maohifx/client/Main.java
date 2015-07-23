/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}

	@Override
	public void start(Stage stage) {
		try {
			ExtFXMLLoader.getGlobalNamespace().put("$stage", stage);
			stage.setTitle("FXML Welcome");
			stage.setScene(new Scene(ExtFXMLLoader.load("http://localhost:8080/maohifx.server/webapi/fxml?id=index"), 800, 600));
			stage.show();
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(4);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(4);
		}
	}
}
