function ContactsController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Contacts");
	}

	tableView.setItems(FXCollections.observableArrayList());
}

ContactsController.prototype.searchEvent = function(aEvent) {
	Contact.search(tableView.getItems(), pattern.getText());
}
