/**
 *
 */
package com.maohi.software.controlsfx.samples;

import org.controlsfx.control.ListSelectionView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ListSelectionViewSample extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {

		final Scene iScene = new Scene(new BorderPane(), 800, 600);

		final ListSelectionView<String> iListSelectionView = new ListSelectionView<>();
		iListSelectionView.getSourceItems().add("Katja");
		iListSelectionView.getSourceItems().add("Dirk");
		iListSelectionView.getSourceItems().add("Philip");
		iListSelectionView.getTargetItems().add("Jule");
		iListSelectionView.getTargetItems().add("Armin");
		iScene.setRoot(iListSelectionView);

		aPrimaryStage.setScene(iScene);
		aPrimaryStage.show();
	}

}
