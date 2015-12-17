package com.maohi.software.maohifx.samples;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Simple draggable node.
 *
 * Dragging code based on {@link http://blog.ngopal.com.np/2011/06/09/draggable-node-in-javafx-2-0/}
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DraggableNode extends Pane {

	// node position
	private double x = 0;
	private double y = 0;
	// mouse position
	private double mousex = 0;
	private double mousey = 0;
	private Node view;
	private boolean dragging = false;
	private boolean moveToFront = true;

	public DraggableNode() {
		this.init();
	}

	public DraggableNode(final Node view) {
		this.view = view;

		this.getChildren().add(view);
		this.init();
	}

	/**
	 * @return the view
	 */
	public Node getView() {
		return this.view;
	}

	private void init() {

		this.onMousePressedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {

				// record the current mouse X and Y position on Node
				DraggableNode.this.mousex = event.getSceneX();
				DraggableNode.this.mousey = event.getSceneY();

				DraggableNode.this.x = DraggableNode.this.getLayoutX();
				DraggableNode.this.y = DraggableNode.this.getLayoutY();

				if (DraggableNode.this.isMoveToFront()) {
					DraggableNode.this.toFront();
				}
			}
		});

		// Event Listener for MouseDragged
		this.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {

				// Get the exact moved X and Y

				final double offsetX = event.getSceneX() - DraggableNode.this.mousex;
				final double offsetY = event.getSceneY() - DraggableNode.this.mousey;

				DraggableNode.this.x += offsetX;
				DraggableNode.this.y += offsetY;

				final double scaledX = DraggableNode.this.x;
				final double scaledY = DraggableNode.this.y;

				DraggableNode.this.setLayoutX(scaledX);
				DraggableNode.this.setLayoutY(scaledY);

				DraggableNode.this.dragging = true;

				// again set current Mouse x AND y position
				DraggableNode.this.mousex = event.getSceneX();
				DraggableNode.this.mousey = event.getSceneY();

				event.consume();
			}
		});

		this.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {

				DraggableNode.this.dragging = false;
			}
		});

	}

	/**
	 * @return the dragging
	 */
	protected boolean isDragging() {
		return this.dragging;
	}

	/**
	 * @return the moveToFront
	 */
	public boolean isMoveToFront() {
		return this.moveToFront;
	}

	public void removeNode(final Node n) {
		this.getChildren().remove(n);
	}

	/**
	 * @param moveToFront
	 *            the moveToFront to set
	 */
	public void setMoveToFront(final boolean moveToFront) {
		this.moveToFront = moveToFront;
	}
}

/**
 * Draggable node sample.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class DraggableNode01 extends Application {

	/**
	 * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores main().
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {

		// we use a default pane without layout such as HBox, VBox etc.
		final Pane root = new Pane();

		final Scene scene = new Scene(root, 800, 600, Color.rgb(160, 160, 160));

		final int numNodes = 6; // number of nodes to add
		final double spacing = 30; // spacing between nodes

		// add numNodes instances of DraggableNode to the root pane
		for (int i = 0; i < numNodes; i++) {
			final DraggableNode node = new DraggableNode();
			node.setPrefSize(98, 80);
			// define the style via css
			node.setStyle("-fx-background-color: #334488; " + "-fx-text-fill: black; " + "-fx-border-color: black;");
			// position the node
			node.setLayoutX((spacing * (i + 1)) + (node.getPrefWidth() * i));
			node.setLayoutY(spacing);
			// add the node to the root pane
			root.getChildren().add(node);
		}

		// finally, show the stage
		primaryStage.setTitle("Draggable Node 01");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}