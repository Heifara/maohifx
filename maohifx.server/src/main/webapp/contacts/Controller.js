load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

function ContactsController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Contacts");
	}

	this.data = FXCollections.observableArrayList();
	tableView.setItems(this.data);
}

ContactsController.prototype.searchEvent = function(aEvent) {
	tableView.setItems(Contact.search(pattern.getText()));
}
