load("http://localhost:8080/maohifx.server/common.js");

MainController = function() {
	this.draggable = true;
	this.xOffset = 0;
	this.yOffset = 0;

	$loader.getNamespace().put("$mainPane", mainPane);
	$loader.getNamespace().put("$tabpane", tabpane);
	$loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab").load(tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoices");
}

MainController.prototype.onMousePressedEvent = function(aEvent) {
	if (this.draggable) {
		this.xOffset = $stage.getX() - aEvent.getScreenX();
		this.yOffset = $stage.getY() - aEvent.getScreenY();
	}
}

MainController.prototype.onMouseDraggedEvent = function(aEvent) {
	if (this.draggable) {
		$stage.setX(aEvent.getScreenX() + this.xOffset);
		$stage.setY(aEvent.getScreenY() + this.yOffset);
	}
}

MainController.prototype.onMouseClickedEvent = function(aEvent) {
	if (aEvent.getButton().equals(MouseButton.PRIMARY)) {
		if (aEvent.getClickCount() == 2) {
			if ($stage.isMaximized()) {
				$stage.setMaximized(false);
				this.draggable = true;
			} else {
				$stage.setMaximized(true);
				this.draggable = false;
			}

		}
	}
}