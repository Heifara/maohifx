load("http://localhost:8080/maohifx.server/common.js");

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
	tableView.setItems(Invoice.search(pattern.getText()));
}
