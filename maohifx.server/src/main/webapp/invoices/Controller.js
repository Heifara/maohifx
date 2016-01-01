function InvoicesController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Factures");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));
}

InvoicesController.prototype.searchEvent = function(aEvent) {
	Invoice.search(tableView.getItems(), pattern.getText());
}
