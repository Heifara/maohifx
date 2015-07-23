/**
 * 
 */
package com.maohi.software.samples.borderpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class BorderPaneSample extends Application {

	@Override
	public void start(Stage aStage) throws Exception {
		BorderPane iRoot = new BorderPane();
		iRoot.getCenter();
		aStage.setScene(new Scene(iRoot));
		aStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
