/**
 * 
 */
package com.maohi.software.maohifx.common.tableview;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class JSONTableViewFXMLSample extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			stage.setTitle("FXMLJSON TableView Sample");
			stage.setWidth(450);
			stage.setHeight(550);
			stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("jsontableviewsample.fxml"))));
			stage.sizeToScene();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}