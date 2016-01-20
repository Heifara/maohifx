/**
 *
 */
package com.maohi.software.maohifx.samples;

import java.net.CookieHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class YoutubeVidePlayer extends Application {

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage aPrimaryStage) throws Exception {
		CookieHandler.setDefault(null);

		final WebView iWebview = new WebView();
		iWebview.getEngine().setJavaScriptEnabled(true);
		iWebview.getEngine().load("https://www.youtube.com/embed/f7dyQ1oSw8g");
		iWebview.setPrefSize(640, 390);

		aPrimaryStage.setScene(new Scene(iWebview));
		aPrimaryStage.show();
	}

}
