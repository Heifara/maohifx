/**
 *
 */
package com.maohi.software.maohifx.samples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Just a very simple sample application that uses the class below.
 */
public class FXTabs extends Application {

	public static void main(final String[] args) {
		launch(FXTabs.class);
	}

	@Override
	public void start(final Stage primaryStage) {

		final DraggableTab tab1 = new DraggableTab("Tab 1");
		tab1.setClosable(false);
		tab1.setDetachable(false);
		tab1.setContent(new Rectangle(500, 500, Color.BLACK));
		final DraggableTab tab2 = new DraggableTab("Tab 2");
		tab2.setClosable(false);
		tab2.setContent(new Rectangle(500, 500, Color.RED));
		final DraggableTab tab3 = new DraggableTab("Tab 3");
		tab3.setClosable(false);
		tab3.setContent(new Rectangle(500, 500, Color.BLUE));
		final DraggableTab tab4 = new DraggableTab("Tab 4");
		tab4.setClosable(false);
		tab4.setContent(new Rectangle(500, 500, Color.ORANGE));
		final TabPane tabs = new TabPane();
		tabs.getTabs().add(tab1);
		tabs.getTabs().add(tab2);
		tabs.getTabs().add(tab3);
		tabs.getTabs().add(tab4);

		final StackPane root = new StackPane();
		root.getChildren().add(tabs);

		final Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
}