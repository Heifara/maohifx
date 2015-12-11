function InvoicePaymentLine() {
	this.uuid = new SimpleStringProperty();
	this.position = new SimpleIntegerProperty();
	this.mode = new SimpleStringProperty();
	this.comment = new SimpleStringProperty();
	this.amount = new SimpleDoubleProperty();
}

InvoicePaymentLine.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		position : this.position.get(),
		paymentMode : this.getPaymentMode(),
		comment : this.comment.get(),
		amount : this.amount.get(),
	};
}

InvoicePaymentLine.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.position.set(aJSONObject.get("position"));
	this.comment.set(aJSONObject.get("comment"));
	this.amount.set(aJSONObject.get("amount"));

	iPaymentMode = new PaymentMode();
	iPaymentMode.parseJSON(aJSONObject.get("paymentMode"));

	this.mode.set(iPaymentMode.label.get());
}

InvoicePaymentLine.prototype.toConsole = function() {
	print(this.toString());
}

InvoicePaymentLine.prototype.getPaymentMode = function() {
	iPaymentMode = new PaymentMode();
	iPaymentMode.label.set(this.mode.get());
	return iPaymentMode.toJSON();
}