function CustomerController() {
	this.customer = new Customer();
	if (typeof ($item) != 'undefined') {
		this.customer.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.customer.contact.lastname);
	}

	this.bindChildren();

	// Auto Completion
	Contact.search(contactAutoCompletion);
}

CustomerController.prototype.bindChildren = function() {
	code.textProperty().bindBidirectional(this.customer.code);
	lastname.textProperty().bindBidirectional(this.customer.contact.lastname);
	middlename.textProperty().bindBidirectional(this.customer.contact.middlename);
	firstname.textProperty().bindBidirectional(this.customer.contact.firstname);
}

CustomerController.prototype.contactAutoCompletionEvent = function(aEvent) {
	this.customer.contact = aEvent.getCompletion();
	this.customer.uuid.set(this.customer.contact.uuid.get());
	this.bindChildren();
}

CustomerController.prototype.saveEvent = function(aActionEvent) {
	this.customer.save();
}
