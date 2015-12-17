/**
 *
 */
package com.maohi.software.maohifx.control;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ControlUtils {

	private static final class DragContext {
		public double mouseAnchorX;
		public double mouseAnchorY;
		public double initialTranslateX;
		public double initialTranslateY;
	}

	public static void makeDraggable(final Node node, final Stage aStage) {
		final DragContext dragContext = new DragContext();

		node.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				// disable mouse events for all children
				mouseEvent.consume();
			}
		});

		node.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				// remember initial mouse cursor coordinates
				// and node position
				dragContext.mouseAnchorX = mouseEvent.getX();
				dragContext.mouseAnchorY = mouseEvent.getY();
				dragContext.initialTranslateX = node.getTranslateX();
				dragContext.initialTranslateY = node.getTranslateY();
			}
		});

		node.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				// shift node from its initial position by delta
				// calculated from mouse cursor movement
				node.setTranslateX((dragContext.initialTranslateX + mouseEvent.getX()) - dragContext.mouseAnchorX);
				node.setTranslateY((dragContext.initialTranslateY + mouseEvent.getY()) - dragContext.mouseAnchorY);

				aStage.setX(node.getTranslateX());
				aStage.setY(node.getTranslateY());

			}
		});
	}

}
