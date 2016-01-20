/**
 *
 */
package com.maohi.software.maohifx.samples;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class MoviePlayerSample extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {
		final Group iRoot = new Group();

		final String iMovie = this.getHostServices().getDocumentBase() + "src/test/resources/test.mp4";
		final Media iMedia = new Media(iMovie);

		final MediaPlayer iMediaPlayer = new MediaPlayer(iMedia);

		final MediaView iMediaView = new MediaView(iMediaPlayer);
		iRoot.getChildren().add(iMediaView);

		aPrimaryStage.setScene(new Scene(iRoot, 800, 600, Color.BLACK));
		aPrimaryStage.show();

		iMediaPlayer.play();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

			}
		});
	}

}
