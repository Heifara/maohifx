function SalesmanController() {
	this.element = new Salesman();
	if (typeof ($item) != 'undefined') {
		this.element.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.element.contact.lastname);
	}

	this.bindChildren();

	// Auto Completion
	Contact.search(contactAutoCompletion);
}

SalesmanController.prototype.bindChildren = function() {
	code.textProperty().bindBidirectional(this.element.code);
	lastname.textProperty().bindBidirectional(this.element.contact.lastname);
	middlename.textProperty().bindBidirectional(this.element.contact.middlename);
	firstname.textProperty().bindBidirectional(this.element.contact.firstname);
	salesCommission.textProperty().bindBidirectional(this.element.salesCommission, new ExtStringConverter());
}

SalesmanController.prototype.contactAutoCompletionEvent = function(aEvent) {
	this.element.contact = aEvent.getCompletion();
	this.element.uuid.set(this.element.contact.uuid.get());
	this.bindChildren();
}

SalesmanController.prototype.saveEvent = function(aActionEvent) {
	this.element.save();
}
