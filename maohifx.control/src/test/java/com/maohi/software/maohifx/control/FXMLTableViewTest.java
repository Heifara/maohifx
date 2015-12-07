/**
 *
 */
package com.maohi.software.maohifx.control;

import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class FXMLTableViewTest extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(FXMLTableViewTest.class);
	}

	@Override
	public void start(final Stage aStage) throws Exception {
		Link.setHrefTarget(HrefTarget.BLANK, new LinkTarget() {

			@Override
			public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
			}

			@Override
			public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final Node aNode) {
				final Stage iStage = new Stage();
				iStage.setScene(new Scene(new BorderPane(aNode)));
				iStage.setTitle(aLink.getText());
				iStage.sizeToScene();
				iStage.show();
			}
		});
		Link.setHrefTarget(HrefTarget.SELF, new LinkTarget() {

			@Override
			public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
			}

			@Override
			public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final Node aNode) {
				aStage.setScene(new Scene(new BorderPane(aNode)));
				aStage.setTitle(aLink.getText());
				aStage.sizeToScene();
				aStage.show();
			}
		});

		final Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("FXMLTableViewTest.fxml")));
		aStage.setTitle("MyTableView Sample");
		aStage.setWidth(450);
		aStage.setHeight(550);
		aStage.setScene(scene);
		aStage.sizeToScene();
		aStage.show();
	}

}
