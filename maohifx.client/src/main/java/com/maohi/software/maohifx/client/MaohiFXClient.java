/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class MaohiFXClient extends Application {

	public static void main(String[] args) {
		Application.launch(MaohiFXClient.class, args);
	}

	@Override
	public void start(Stage stage) {
		try {
			Scene iScene = new Scene(new BorderPane());
			stage.setScene(iScene);

			ExtFXMLLoader iParentLoader = new ExtFXMLLoader();
			iParentLoader.getNamespace().put("$stage", stage);
			iParentLoader.getNamespace().put("$scene", iScene);

			iScene.setRoot(iParentLoader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=index").load());
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
