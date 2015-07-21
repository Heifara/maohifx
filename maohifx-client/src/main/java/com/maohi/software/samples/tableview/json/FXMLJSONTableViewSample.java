package com.maohi.software.samples.tableview.json;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLJSONTableViewSample extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			stage.setTitle("FXMLJSON TableView Sample");
			stage.setWidth(450);
			stage.setHeight(550);
			stage.setScene(new Scene(FXMLLoader.load(new URL("http://localhost:8080/maohifx-server/webapi/fxml?id=jsontableview"))));
			stage.sizeToScene();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}