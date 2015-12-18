load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Customer.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

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
	tableView.setItems(Customer.search(pattern.getText()));
}
