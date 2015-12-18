load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Supplier.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

function SupplierController() {
	this.supplier = new Supplier();
	if (typeof ($item) != 'undefined') {
		this.supplier.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.supplier.contact.lastname);
	}

	code.textProperty().bindBidirectional(this.supplier.code);
	lastname.textProperty().bindBidirectional(this.supplier.contact.lastname);
	middlename.textProperty().bindBidirectional(this.supplier.contact.middlename);
	firstname.textProperty().bindBidirectional(this.supplier.contact.firstname);
}

SupplierController.prototype.saveEvent = function(aActionEvent) {
	this.supplier.save();
}
