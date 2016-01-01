function CustomersController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Clients");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));
}

CustomersController.prototype.searchEvent = function(aEvent) {
	Customer.search(tableView.getItems(), pattern.getText());
}
