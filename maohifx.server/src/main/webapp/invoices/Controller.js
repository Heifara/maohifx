function InvoicesController() {
	this.text = "Nouveau";

	if (typeof ($tab) != 'undefined') {
		$tab.setText("Factures");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));

	runLater(new Runnable({
		run : function() {
			var iNotifications = Notifications.create();
			iNotifications.title("Hello World");
			iNotifications.position(Pos.BOTTOM_RIGHT);
			iNotifications.show();
		}
	}));
}

InvoicesController.prototype.searchEvent = function(aEvent) {
	Invoice.search(tableView.getItems(), pattern.getText());
}
