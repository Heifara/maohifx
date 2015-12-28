function SalesmanController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Vendeurs");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));
}

SalesmanController.prototype.searchEvent = function(aEvent) {
	tableView.setItems(Salesman.search(pattern.getText()));
}
