/**
 * 
 */
package com.maohi.software.maohifx.samples.gridpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class GridPaneSample extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage aStage) throws Exception {
		TextField iTextField = new TextField();
		iTextField.setFocusTraversable(false);
		GridPane iGridPane = new GridPane();

		iGridPane.add(new Label("this is a text"), 0, 0);
		iGridPane.add(new Label("this is a text"), 0, 1);

		aStage.setScene(new Scene(iGridPane, 400, 200));
		aStage.show();
	}

}
