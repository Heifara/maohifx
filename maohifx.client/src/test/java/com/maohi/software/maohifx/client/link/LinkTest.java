/**
 * 
 */
package com.maohi.software.maohifx.client.link;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class LinkTest extends Application {

	public static void main(String[] aArgs) {
		launch(LinkTest.class);
	}

	@Override
	public void start(Stage aStage) throws Exception {
		aStage.setScene(new Scene(new Link()));
		aStage.setTitle("ExtendedTab");
		aStage.setWidth(600);
		aStage.setHeight(400);
		aStage.show();
	}

}
