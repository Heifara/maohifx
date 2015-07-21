load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

controller = new Controller();

function Controller() {
	this.invoice = new Invoice();
}

Controller.prototype.init = function() {
	invoiceNumber.setText(this.invoice.invoiceNumber);
}

Controller.prototype.saveEvent = function() {
	print("saved");
}

Controller.prototype.printEvent = function() {
	print("printed");
}
