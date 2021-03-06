/**
 *
 */
package com.maohi.software.maohifx.samples;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * A draggable tab that can optionally be detached from its tab pane and shown in a separate window. This can be added to any normal TabPane, however a TabPane with draggable tabs must *only* have DraggableTabs, normal tabs and DrragableTabs mixed will cause issues!
 * <p>
 *
 * @author Michael Berry
 */
public class DraggableTab extends Tab {

	private static class InsertData {

		private final int index;
		private final TabPane insertPane;

		public InsertData(final int index, final TabPane insertPane) {
			this.index = index;
			this.insertPane = insertPane;
		}

		public int getIndex() {
			return this.index;
		}

		public TabPane getInsertPane() {
			return this.insertPane;
		}

	}

	private static final Set<TabPane> tabPanes = new HashSet<>();
	private static final Stage markerStage;

	static {
		markerStage = new Stage();
		markerStage.initStyle(StageStyle.UNDECORATED);
		final Rectangle dummy = new Rectangle(3, 10, Color.web("#555555"));
		final StackPane markerStack = new StackPane();
		markerStack.getChildren().add(dummy);
		markerStage.setScene(new Scene(markerStack));
	}

	private Label nameLabel;
	private Text dragText;

	private Stage dragStage;

	private boolean detachable;

	/**
	 * Create a new draggable tab. This can be added to any normal TabPane, however a TabPane with draggable tabs must *only* have DraggableTabs, normal tabs and DrragableTabs mixed will cause issues!
	 * <p>
	 *
	 * @param text
	 *            the text to appear on the tag label.
	 */
	public DraggableTab(final String text) {
		this.nameLabel = new Label(text);
		this.setGraphic(this.nameLabel);
		this.detachable = true;
		this.dragStage = new Stage();
		this.dragStage.initStyle(StageStyle.UNDECORATED);
		final StackPane dragStagePane = new StackPane();
		dragStagePane.setStyle("-fx-background-color:#DDDDDD;");
		this.dragText = new Text(text);
		StackPane.setAlignment(this.dragText, Pos.CENTER);
		dragStagePane.getChildren().add(this.dragText);
		this.dragStage.setScene(new Scene(dragStagePane));
		this.nameLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent t) {
				DraggableTab.this.dragStage.setWidth(DraggableTab.this.nameLabel.getWidth() + 10);
				DraggableTab.this.dragStage.setHeight(DraggableTab.this.nameLabel.getHeight() + 10);
				DraggableTab.this.dragStage.setX(t.getScreenX());
				DraggableTab.this.dragStage.setY(t.getScreenY());
				DraggableTab.this.dragStage.show();
				final Point2D screenPoint = new Point2D(t.getScreenX(), t.getScreenY());
				tabPanes.add(DraggableTab.this.getTabPane());
				final InsertData data = DraggableTab.this.getInsertData(screenPoint);
				if ((data == null) || data.getInsertPane().getTabs().isEmpty()) {
					markerStage.hide();
				} else {
					int index = data.getIndex();
					boolean end = false;
					if (index == data.getInsertPane().getTabs().size()) {
						end = true;
						index--;
					}
					final Rectangle2D rect = DraggableTab.this.getAbsoluteRect(data.getInsertPane().getTabs().get(index));
					if (end) {
						markerStage.setX(rect.getMaxX() + 13);
					} else {
						markerStage.setX(rect.getMinX());
					}
					markerStage.setY(rect.getMaxY() + 10);
					markerStage.show();
				}
			}
		});
		this.nameLabel.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent t) {
				markerStage.hide();
				DraggableTab.this.dragStage.hide();
				if (!t.isStillSincePress()) {
					final Point2D screenPoint = new Point2D(t.getScreenX(), t.getScreenY());
					final TabPane oldTabPane = DraggableTab.this.getTabPane();
					final int oldIndex = oldTabPane.getTabs().indexOf(DraggableTab.this);
					tabPanes.add(oldTabPane);
					final InsertData insertData = DraggableTab.this.getInsertData(screenPoint);
					if (insertData != null) {
						int addIndex = insertData.getIndex();
						if ((oldTabPane == insertData.getInsertPane()) && (oldTabPane.getTabs().size() == 1)) {
							return;
						}
						oldTabPane.getTabs().remove(DraggableTab.this);
						if ((oldIndex < addIndex) && (oldTabPane == insertData.getInsertPane())) {
							addIndex--;
						}
						if (addIndex > insertData.getInsertPane().getTabs().size()) {
							addIndex = insertData.getInsertPane().getTabs().size();
						}
						insertData.getInsertPane().getTabs().add(addIndex, DraggableTab.this);
						insertData.getInsertPane().selectionModelProperty().get().select(addIndex);
						return;
					}
					if (!DraggableTab.this.detachable) {
						return;
					}
					final Stage newStage = new Stage();
					final TabPane pane = new TabPane();
					tabPanes.add(pane);
					newStage.setOnHiding(new EventHandler<WindowEvent>() {

						@Override
						public void handle(final WindowEvent t) {
							tabPanes.remove(pane);
						}
					});
					DraggableTab.this.getTabPane().getTabs().remove(DraggableTab.this);
					pane.getTabs().add(DraggableTab.this);
					pane.getTabs().addListener(new ListChangeListener<Tab>() {

						@Override
						public void onChanged(final ListChangeListener.Change<? extends Tab> change) {
							if (pane.getTabs().isEmpty()) {
								newStage.hide();
							}
						}
					});
					newStage.setScene(new Scene(pane));
					newStage.initStyle(StageStyle.UTILITY);
					newStage.setX(t.getScreenX());
					newStage.setY(t.getScreenY());
					newStage.show();
					pane.requestLayout();
					pane.requestFocus();
				}
			}

		});
	}

	private boolean betweenX(final Rectangle2D r1, final Rectangle2D r2, final double xPoint) {
		final double lowerBound = r1.getMinX() + (r1.getWidth() / 2);
		final double upperBound = r2.getMaxX() - (r2.getWidth() / 2);
		return (xPoint >= lowerBound) && (xPoint <= upperBound);
	}

	private Rectangle2D getAbsoluteRect(final Control node) {
		return new Rectangle2D(node.localToScene(node.getLayoutBounds().getMinX(), node.getLayoutBounds().getMinY()).getX() + node.getScene().getWindow().getX(), node.localToScene(node.getLayoutBounds().getMinX(), node.getLayoutBounds().getMinY()).getY() + node.getScene().getWindow().getY(), node.getWidth(), node.getHeight());
	}

	private Rectangle2D getAbsoluteRect(final Tab tab) {
		final Control node = ((DraggableTab) tab).getLabel();
		return this.getAbsoluteRect(node);
	}

	private InsertData getInsertData(final Point2D screenPoint) {
		for (final TabPane tabPane : tabPanes) {
			final Rectangle2D tabAbsolute = this.getAbsoluteRect(tabPane);
			if (tabAbsolute.contains(screenPoint)) {
				int tabInsertIndex = 0;
				if (!tabPane.getTabs().isEmpty()) {
					final Rectangle2D firstTabRect = this.getAbsoluteRect(tabPane.getTabs().get(0));
					if (((firstTabRect.getMaxY() + 60) < screenPoint.getY()) || (firstTabRect.getMinY() > screenPoint.getY())) {
						return null;
					}
					final Rectangle2D lastTabRect = this.getAbsoluteRect(tabPane.getTabs().get(tabPane.getTabs().size() - 1));
					if (screenPoint.getX() < (firstTabRect.getMinX() + (firstTabRect.getWidth() / 2))) {
						tabInsertIndex = 0;
					} else if (screenPoint.getX() > (lastTabRect.getMaxX() - (lastTabRect.getWidth() / 2))) {
						tabInsertIndex = tabPane.getTabs().size();
					} else {
						for (int i = 0; i < (tabPane.getTabs().size() - 1); i++) {
							final Tab leftTab = tabPane.getTabs().get(i);
							final Tab rightTab = tabPane.getTabs().get(i + 1);
							if ((leftTab instanceof DraggableTab) && (rightTab instanceof DraggableTab)) {
								final Rectangle2D leftTabRect = this.getAbsoluteRect(leftTab);
								final Rectangle2D rightTabRect = this.getAbsoluteRect(rightTab);
								if (this.betweenX(leftTabRect, rightTabRect, screenPoint.getX())) {
									tabInsertIndex = i + 1;
									break;
								}
							}
						}
					}
				}
				return new InsertData(tabInsertIndex, tabPane);
			}
		}
		return null;
	}

	private Label getLabel() {
		return this.nameLabel;
	}

	/**
	 * Set whether it's possible to detach the tab from its pane and move it to another pane or another window. Defaults to true.
	 * <p>
	 *
	 * @param detachable
	 *            true if the tab should be detachable, false otherwise.
	 */
	public void setDetachable(final boolean detachable) {
		this.detachable = detachable;
	}

	/**
	 * Set the label text on this draggable tab. This must be used instead of setText() to set the label, otherwise weird side effects will result!
	 * <p>
	 *
	 * @param text
	 *            the label text for this tab.
	 */
	public void setLabelText(final String text) {
		this.nameLabel.setText(text);
		this.dragText.setText(text);
	}
}