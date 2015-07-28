function Invoice() {
	this.uuid = new SimpleStringProperty();
	this.invoiceNumber = new SimpleIntegerProperty();
	this.invoiceDate = new SimpleObjectProperty();

	this.invoiceLines = FXCollections.observableArrayList();
}

Invoice.prototype.add = function(aText) {
	iInvoiceLine = new InvoiceLine();
	iInvoiceLine.label.set(aText);
	iInvoiceLine.position.set(this.invoiceLines.size());
	this.invoiceLines.add(iInvoiceLine);
}

Invoice.prototype.remove = function(aIndex) {
	print(aIndex);
	this.invoiceLines.remove(aIndex);
}

Invoice.prototype.getTabTitle = function() {
	return "Facture n°" + this.invoiceNumber.get();
}

Invoice.prototype.print = function() {
	for (var iIndex = 0; iIndex < this.invoiceLines.size(); iIndex++) {
		iInvoiceLine = this.invoiceLines.get(iIndex);
		iInvoiceLine.toConsole();
	}
}

Invoice.prototype.save = function() {
	$loader.getNamespace().put("$invoice", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/invoice",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : {
			uuid : this.uuid.get(),
			number : this.invoiceNumber.get(),
			date : this.invoiceDate.getValue().toString()
		},
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Invoice.js");
			load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

			$invoice.parseJSON($result);
		},
		error : function($result, $status) {
			print($status);
		}
	});
}

Invoice.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.invoiceNumber.set(aJSONObject.get("number"));
	this.invoiceDate.set(aJSONObject.get("date"));
}