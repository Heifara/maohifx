function InvoiceLine() {
	this.uuid = new SimpleStringProperty();
	this.position = new SimpleIntegerProperty();
	this.barCode = new SimpleStringProperty();
	this.label = new SimpleStringProperty();
	this.quantity = new SimpleDoubleProperty();
	this.sellingPrice = new SimpleDoubleProperty();
	this.discountRate = new SimpleDoubleProperty();
	this.tvaRate = new SimpleDoubleProperty();
	this.sellingPriceWithTaxes = Bindings.multiply(this.sellingPrice, this.tvaRate.divide(100).add(1));

	this.tvaAmount = Bindings.multiply(this.sellingPrice, this.tvaRate.divide(100));
	this.discountAmount = Bindings.multiply(this.sellingPrice, this.discountRate.divide(100));

	this.totalAmount = Bindings.multiply(this.sellingPrice, this.quantity).add(this.tvaAmount).subtract(this.discountAmount);
}

InvoiceLine.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		position : this.position.get(),
		barCode : this.barCode.get(),
		label : this.label.get(),
		quantity : this.quantity.get(),
		sellingPrice : this.sellingPrice.get(),
		discountRate : this.discountRate.get(),
		tvaRate : this.tvaRate.get()
	};
}

InvoiceLine.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.position.set(aJSONObject.get("position"));
	this.barCode.set(aJSONObject.get("barCode"));
	this.label.set(aJSONObject.get("label"));
	this.quantity.set(aJSONObject.get("quantity"));
	this.sellingPrice.set(aJSONObject.get("sellingPrice"));
	this.discountRate.set(aJSONObject.get("discountRate"));
	this.tvaRate.set(aJSONObject.get("tvaRate"));
}

InvoiceLine.prototype.toConsole = function() {
	print(this.toString());
}