/**
 *
 */
package com.maohi.software.controlsfx.samples;

import org.controlsfx.control.InfoOverlay;

import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class InfoOverlaySample extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {

		final Scene iScene = new Scene(new BorderPane(), 800, 600);

		final String iText = "Hello World!! This is an InfoOverlay demo.";

		final ImageView iImageView = new ImageView(new Image("background.jpg"));
		iImageView.fitWidthProperty().bind(iScene.widthProperty());
		iImageView.fitHeightProperty().bind(iScene.heightProperty());

		final InfoOverlay iInfoOverlay = new InfoOverlay(iImageView, iText);
		iInfoOverlay.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		iInfoOverlay.setShowOnHover(true);
		iScene.setRoot(iInfoOverlay);

		aPrimaryStage.setScene(iScene);
		aPrimaryStage.show();
	}

}
