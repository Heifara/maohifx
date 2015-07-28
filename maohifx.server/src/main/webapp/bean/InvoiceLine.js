function InvoiceLine() {
	this.position = new SimpleIntegerProperty();
	this.label = new SimpleStringProperty();
	this.sellingPrice = new SimpleStringProperty();
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