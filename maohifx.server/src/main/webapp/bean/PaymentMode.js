function PaymentMode() {
	this.label = new SimpleStringProperty();
}

PaymentMode.prototype.toJSON = function() {
	return {
		label : this.label.get()
	};
}

PaymentMode.prototype.parseJSON = function(aJSONObject) {
	this.label.set(aJSONObject.get("label"));
}

PaymentMode.prototype.toConsole = function() {
	print(this.toString());
}