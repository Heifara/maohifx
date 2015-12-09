/**
 *
 */
package com.maohi.software.maohifx.control;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.maohi.software.maohifx.control.Link.LinkTarget;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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
			public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final String aRecipeeId) {
				try {
					final Stage iStage = new Stage();
					iStage.setScene(new Scene(new BorderPane(aLoader.load())));
					iStage.setTitle(aLink.getText());
					iStage.sizeToScene();
					iStage.show();
				} catch (final IOException aException) {
					JOptionPane.showMessageDialog(null, aException.getMessage());
				}
			}
		});
		Link.setHrefTarget(HrefTarget.SELF, new LinkTarget() {

			@Override
			public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
			}

			@Override
			public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final String aRecipeeId) {
				try {
					aStage.setScene(new Scene(new BorderPane(aLoader.load())));
					aStage.setTitle(aLink.getText());
					aStage.sizeToScene();
					aStage.show();
				} catch (final IOException aException) {
					JOptionPane.showMessageDialog(null, aException.getMessage());
				}
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
