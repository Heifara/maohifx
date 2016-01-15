/**
 *
 */
package com.maohi.software.controlsfx.samples;

import org.controlsfx.control.Notifications;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class NotificationsSample extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {
		final Scene iScene = new Scene(new BorderPane(), 800, 600);

		aPrimaryStage.setScene(iScene);
		aPrimaryStage.show();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				final Notifications iNotifications = Notifications.create();
				// iNotifications.owner(aPrimaryStage);
				iNotifications.title("Hello World");
				iNotifications.position(Pos.BOTTOM_RIGHT);
				iNotifications.show();
			}
		});
	}

}
