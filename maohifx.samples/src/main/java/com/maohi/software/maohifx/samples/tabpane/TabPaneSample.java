/**
 * 
 */
package com.maohi.software.maohifx.samples.tabpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class TabPaneSample extends Application {

	@Override
	public void start(Stage aStage) throws Exception {
		TabPane iTabPane = new TabPane();
		iTabPane.getTabs().add(new Tab("Nouvelle Onglet"));

		BorderPane iRoot = new BorderPane(iTabPane);
		aStage.setScene(new Scene(iRoot, 600, 400));
		aStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
