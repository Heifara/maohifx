/**
 *
 */
package com.maohi.software.maohifx.control;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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
public class LinkTest extends Application {

	public static void main(final String[] aArgs) {
		launch(LinkTest.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Override
	public void start(final Stage aStage) throws Exception {
		final BorderPane iBorderPane = new BorderPane();

		Link.setHrefTarget(HrefTarget.BLANK, new LinkTarget() {

			@Override
			public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
				aException.printStackTrace();
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
				aException.printStackTrace();
			}

			@Override
			public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final Node aNode) {
				iBorderPane.setBottom(null);
				iBorderPane.setBottom(aNode);
				aStage.sizeToScene();
			}
		});

		final Link iCurrentStageLink = new Link();
		iBorderPane.setCenter(iCurrentStageLink);
		iCurrentStageLink.setText("Open in current stage");
		iCurrentStageLink.setHref("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
		iCurrentStageLink.setTarget(HrefTarget.SELF);

		final Link iNewStage = new Link();
		iBorderPane.setTop(iNewStage);
		iNewStage.setText("Open in new stage");
		iNewStage.setHref("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
		iNewStage.setTarget(HrefTarget.BLANK);

		aStage.setTitle("MyTableView Sample");
		aStage.setWidth(450);
		aStage.setHeight(550);
		aStage.setScene(new Scene(iBorderPane));
		aStage.sizeToScene();
		aStage.show();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
