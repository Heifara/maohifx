function Invoice() {
	this.uuid = new SimpleStringProperty();
	this.invoiceNumber = new SimpleIntegerProperty();
	this.invoiceDate = new SimpleLocalDateProperty();
	this.customerName = new SimpleStringProperty();

	this.invoiceLines = FXCollections.observableArrayList();
}

Invoice.prototype.add = function(aText) {
	iInvoiceLine = new InvoiceLine();
	iInvoiceLine.uuid.set(java.util.UUID.randomUUID().toString());
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
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/invoice/pdf",
		type : "get",
		success : function($result, $status) {
			print($result);
			print($status);
			java.lang.Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + $result);
		},
		error : function($result, $status) {
			print($status);
		}
	});
}

Invoice.prototype.getInvoiceLines = function() {
	iArrayList = new java.util.ArrayList();
	for ( var index in this.invoiceLines) {
		iArrayList.add(this.invoiceLines.get(index).toJSON());
	}

	print(iArrayList);
	return iArrayList;
}

Invoice.prototype.save = function() {
	$loader.getNamespace().put("$invoice", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/invoice",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
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

Invoice.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		number : this.invoiceNumber.get(),
		date : this.invoiceDate.getDate(),
		customerName : this.customerName.get(),
		invoiceLines : this.getInvoiceLines()
	}
}

Invoice.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.invoiceNumber.set(aJSONObject.get("number"));
	this.invoiceDate.setDate(aJSONObject.get("date"));
	this.customerName.set(aJSONObject.get("customerName"));

	this.invoiceLines.clear();
	iArray = aJSONObject.get("invoiceLines");
	for ( var iIndex in iArray) {
		iInvoiceLine = new InvoiceLine();
		iInvoiceLine.parseJSON(iArray[iIndex]);
		this.invoiceLines.add(iInvoiceLine);
	}
}