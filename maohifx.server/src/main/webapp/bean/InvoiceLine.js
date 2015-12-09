function InvoiceLine() {
	this.uuid = new SimpleStringProperty();
	this.position = new SimpleIntegerProperty();
	this.label = new SimpleStringProperty();
	this.sellingPrice = new SimpleDoubleProperty();
	this.quantity = new SimpleDoubleProperty();
	this.discount = new SimpleDoubleProperty();
	this.tvaRate = new SimpleDoubleProperty();
	this.sellingPriceWithTaxes = Bindings.multiply(this.sellingPrice, this.tvaRate.divide(100).add(1));

	this.tvaAmount = Bindings.multiply(this.sellingPrice, this.tvaRate.divide(100));
	this.discountAmount = Bindings.multiply(this.sellingPrice, this.discount.divide(100));

	this.totalAmount = Bindings.multiply(this.sellingPrice, this.quantity).add(this.tvaAmount).subtract(this.discountAmount);
}

InvoiceLine.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		position : this.position.get(),
		label : this.label.get(),
		sellingPrice : this.sellingPrice.get(),
	};
}

InvoiceLine.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.position.set(aJSONObject.get("position"));
	this.label.set(aJSONObject.get("label"));
	this.sellingPrice.set(aJSONObject.get("sellingPrice"));
	this.quantity.set(aJSONObject.get("quantity"));
}

InvoiceLine.prototype.toString = function() {
	iString = new StringBuilder();
	iString.append("{\n");
	iString.append("position:" + this.position.get() + ",\n");
	iString.append("label:" + this.label.get() + "\n");
	iString.append("}");
	return iString.toString();
}

InvoiceLine.prototype.toConsole = function() {
	print(this.toString());
}