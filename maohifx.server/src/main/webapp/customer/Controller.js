load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Customer.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

function CustomerController() {
	this.customer = new Customer();
	if (typeof ($item) != 'undefined') {
		this.customer.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.customer.contact.lastname);
	}

	code.textProperty().bindBidirectional(this.customer.code);
	lastname.textProperty().bindBidirectional(this.customer.contact.lastname);
	middlename.textProperty().bindBidirectional(this.customer.contact.middlename);
	firstname.textProperty().bindBidirectional(this.customer.contact.firstname);
}

CustomerController.prototype.saveEvent = function(aActionEvent) {
	this.customer.save();
}
