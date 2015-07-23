/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
			FXMLLoader iLoader = new FXMLLoader(new URL("http://localhost:8080/maohifx.server/webapi/fxml?id=index"));
			Parent root = iLoader.load();
			stage.setTitle("FXML Welcome");
			stage.setScene(new Scene(root, 800, 600));
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
