load("http://localhost:8080/maohifx.server/common.js");

function SupplierController() {
	this.supplier = new Supplier();
	if (typeof ($item) != 'undefined') {
		this.supplier.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.supplier.contact.lastname);
	}

	this.bindChildren();

	// Auto Completion
	contactAutoCompletion.addAll(Contact.search());
}

SupplierController.prototype.bindChildren = function() {
	code.textProperty().bindBidirectional(this.supplier.code);
	lastname.textProperty().bindBidirectional(this.supplier.contact.lastname);
	middlename.textProperty().bindBidirectional(this.supplier.contact.middlename);
	firstname.textProperty().bindBidirectional(this.supplier.contact.firstname);
}

SupplierController.prototype.contactAutoCompletionEvent = function(aEvent) {
	this.supplier.contact = aEvent.getCompletion();
	this.supplier.uuid.set(this.supplier.contact.uuid.get());
	this.bindChildren();
}

SupplierController.prototype.saveEvent = function(aActionEvent) {
	this.supplier.save();
}
