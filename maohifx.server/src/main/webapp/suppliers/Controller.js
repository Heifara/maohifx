load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Supplier.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

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
