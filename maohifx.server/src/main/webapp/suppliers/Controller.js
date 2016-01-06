function SuppliersController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Fournisseurs");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));
}

SuppliersController.prototype.searchEvent = function(aEvent) {
	tableView.setItems(Supplier.search(pattern.getText()));
}
