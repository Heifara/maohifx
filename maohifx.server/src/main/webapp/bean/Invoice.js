function Invoice() {
	this.uuid = new SimpleStringProperty();
	this.invoiceNumber = new SimpleIntegerProperty();
	this.invoiceDate = new SimpleLocalDateProperty();
	this.customerName = new SimpleStringProperty();
	this.href = new SimpleStringProperty();

	this.totalWithNoTaxes = new SimpleDoubleProperty();
	this.totalTva = new SimpleDoubleProperty();
	this.totalDiscount = new SimpleDoubleProperty();
	this.totalWithTaxes = new SimpleDoubleProperty();
	this.totalPaidAmount = new SimpleDoubleProperty();
	this.totalChange = new SimpleDoubleProperty();

	this.invoiceLines = FXCollections.observableArrayList();
	this.invoicePaymentLines = FXCollections.observableArrayList();
}

Invoice.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		number : this.invoiceNumber.get(),
		date : this.invoiceDate.getDate(),
		customerName : this.customerName.get(),
		invoiceLines : this.getInvoiceLines(),
		invoicePaymentLines : this.getInvoicePaymentLines(),
	}
}

Invoice.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.invoiceNumber.set(aJSONObject.get("number"));
	this.invoiceDate.setDate(aJSONObject.get("date"));
	this.customerName.set(aJSONObject.get("customerName"));
	this.href.set("http://localhost:8080/maohifx.server/webapi/invoice?uuid=" + this.uuid.get() + "");

	this.invoiceLines.clear();
	iArray = aJSONObject.get("invoiceLines");
	for ( var iIndex in iArray) {
		iInvoiceLine = new InvoiceLine();
		iInvoiceLine.parseJSON(iArray[iIndex]);
		this.invoiceLines.add(iInvoiceLine);
	}

	this.invoicePaymentLines.clear();
	iArray = aJSONObject.get("invoicePaymentLines");
	for ( var iIndex in iArray) {
		iInvoicePaymentLine = new InvoicePaymentLine();
		iInvoicePaymentLine.parseJSON(iArray[iIndex]);
		this.invoicePaymentLines.add(iInvoicePaymentLine);
	}
}

Invoice.prototype.updateTotals = function() {
	iTotalWithNoTaxes = 0.0;
	iTotalTva = 0.0;
	iTotalDiscount = 0.0;
	iTotalWithTaxes = 0.0;
	for (iIndex in this.invoiceLines) {
		iInvoiceLine = this.invoiceLines.get(iIndex);
		iTotalWithNoTaxes += iInvoiceLine.sellingPrice.get() * iInvoiceLine.quantity.get();
		iTotalTva += iInvoiceLine.tvaAmount.get();
		iTotalDiscount += iInvoiceLine.discountAmount.get();
		iTotalWithTaxes += iInvoiceLine.totalAmount.get();
	}

	iTotalPaidAmount = 0.0;
	for (iIndex in this.invoicePaymentLines) {
		iInvoicePaymentLine = this.invoicePaymentLines.get(iIndex);
		iTotalPaidAmount += iInvoicePaymentLine.amount.get();
	}

	this.totalWithNoTaxes.set(iTotalWithNoTaxes);
	this.totalTva.set(iTotalTva);
	this.totalDiscount.set(iTotalDiscount);
	this.totalWithTaxes.set(iTotalWithTaxes);
	this.totalChange.set(iTotalPaidAmount - iTotalWithTaxes);
}

Invoice.prototype.addInvoiceLine = function() {
	iInvoiceLine = new InvoiceLine();
	iInvoiceLine.uuid.set(java.util.UUID.randomUUID().toString());
	iInvoiceLine.position.set(this.invoiceLines.size());
	iInvoiceLine.label.set("");
	iInvoiceLine.quantity.set(1.0);
	iInvoiceLine.sellingPrice.set(0.0);
	iInvoiceLine.discountRate.set(0.0);
	iInvoiceLine.tvaRate.set(0.0);

	this.invoiceLines.add(iInvoiceLine);
}

Invoice.prototype.addInvoicePaymentLine = function() {
	iInvoicePaymentLine = new InvoicePaymentLine();
	iInvoicePaymentLine.uuid.set(java.util.UUID.randomUUID().toString());
	iInvoicePaymentLine.position.set(this.invoicePaymentLines.size());
	iInvoicePaymentLine.mode.set("");
	iInvoicePaymentLine.comment.set("");
	iInvoicePaymentLine.amount.set(0.0);

	this.invoicePaymentLines.add(iInvoicePaymentLine)
}

Invoice.prototype.remove = function(aIndex) {
	this.invoiceLines.remove(aIndex);

	this.updateTotals();
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

Invoice.prototype.getLastInvoiceLine = function() {
	if (this.invoiceLines.size() == 0) {
		return null;
	}
	return this.invoiceLines.get(this.invoiceLines.size() - 1);
}

Invoice.prototype.getLastInvoicePaymentLine = function() {
	if (this.invoicePaymentLines.size() == 0) {
		return null;
	}
	return this.invoicePaymentLines.get(this.invoicePaymentLines.size() - 1);
}

Invoice.prototype.removeLastInvoiceLine = function() {
	if (this.invoiceLines.size() > 0) {
		this.invoiceLines.remove(this.invoiceLines.size() - 1);
	}
}

Invoice.prototype.removeLastInvoicePaymentLine = function() {
	if (this.invoicePaymentLines.size() > 0) {
		this.invoicePaymentLines.remove(this.invoicePaymentLines.size() - 1);
	}
}

Invoice.prototype.getInvoiceLines = function() {
	iArrayList = new java.util.ArrayList();
	for ( var index in this.invoiceLines) {
		iArrayList.add(this.invoiceLines.get(index).toJSON());
	}

	return iArrayList;
}

Invoice.prototype.getInvoicePaymentLines = function() {
	iArrayList = new java.util.ArrayList();
	for ( var index in this.invoicePaymentLines) {
		iArrayList.add(this.invoicePaymentLines.get(index).toJSON());
	}

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

			alert("Save success!");
		},
		error : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			print($status);

			alert("Save error!");
		}
	});
}