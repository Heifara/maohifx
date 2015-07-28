package com.maohi.software.maohifx.samples.tabpane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/** https://forums.oracle.com/forums/thread.jspa?messageID=10573836 "Thread: Adding button next to tab " */
public class ButtonsInTabArea extends Application {
	public static void main(final String[] args) {
		launch(args);
	}

	private TabPane createButtonInTabsTabPane() {
		final TabPane tabPane = this.createTabPane();
		int i = 1;
		for (final Tab tab : tabPane.getTabs()) {
			this.placeButtonInTab("ButtonInTab " + i, tab);
			i++;
		}
		return tabPane;
	}

	private Group createFixedWidthTabPaneWithButtonOnTop() {
		final TabPane tabPane = this.createTabPane();
		final Button button = new Button("Button on top of fixed size TabPane");

		final Group layout = new Group(tabPane, button);
		button.layoutXProperty().bind(tabPane.widthProperty().subtract(button.widthProperty().add(10.0)));
		button.setLayoutY(5);

		return layout;
	}

	private Pane createSideBySideButtonAndTabPane() {
		final HBox hbox = new HBox(5);
		final TabPane tabPane = this.createTabPane();
		final Button button = new Button("Button beside TabPane");
		hbox.getChildren().addAll(tabPane, button);

		return hbox;
	}

	private Tab createTab(final String color) {
		final Tab tab = new Tab(color);
		tab.setContent(new Rectangle(500, 75, Color.valueOf(color)));
		return tab;
	}

	private TabPane createTabPane() {
		final TabPane tabPane = new TabPane();
		tabPane.getTabs().addAll(this.createTab("red"), this.createTab("green"), this.createTab("blue"));
		for (int i = 0; i < 15; i++) {
			tabPane.getTabs().add(new Tab("Tab " + i));
		}
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.setMaxWidth(Region.USE_PREF_SIZE);

		return tabPane;
	}

	private Pane createVariableWidthTabPaneWithButtonOnTop() {
		final TabPane tabPane = this.createTabPane();
		final Button button = new Button("Button on top of variable size TabPane");
		button.setMinWidth(Region.USE_PREF_SIZE);

		final Pane layout = new AnchorPane();
		layout.getChildren().addAll(tabPane, button);
		AnchorPane.setTopAnchor(button, 5.0);
		AnchorPane.setRightAnchor(button, 10.0);
		layout.maxWidthProperty().bind(tabPane.widthProperty());

		return layout;
	}

	private void placeButtonInTab(final String buttonText, final Tab tab) {
		final Button button = new Button(buttonText);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				tab.getTabPane().getSelectionModel().select(tab);
			}
		});
		tab.setGraphic(button);
	}

	@Override
	public void start(final Stage stage) {
		final VBox layout = new VBox(10);
		layout.getChildren().addAll(this.createTabPane(), this.createButtonInTabsTabPane(), this.createSideBySideButtonAndTabPane(), this.createFixedWidthTabPaneWithButtonOnTop(), this.createVariableWidthTabPaneWithButtonOnTop());
		layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

		stage.setScene(new Scene(layout));
		stage.show();
	}
}