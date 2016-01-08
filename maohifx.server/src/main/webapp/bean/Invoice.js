Invoice.search = function(aCollection, aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	$http.ajax({
		url : "@maohifx.server/webapi/invoice/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear()
			for ( var item in $result) {
				iInvoice = new Invoice();
				iInvoice.parseJSON($result[item]);
				aCollection.add(iInvoice);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
}

function Invoice() {
	this.uuid = new SimpleStringProperty();
	this.invoiceNumber = new SimpleIntegerProperty();
	this.invoiceDate = new SimpleLocalDateProperty();
	this.validDate = new SimpleLocalDateProperty();
	this.customerName = new SimpleStringProperty();
	this.customer = null;
	this.salesman = null;
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
		validDate : this.validDate.getDate(),
		customerName : this.customerName.get(),
		customer : this.customer.toJSON(),
		salesman : this.salesman.toJSON(),
		invoiceLines : this.getInvoiceLines(),
		invoicePaymentLines : this.getInvoicePaymentLines(),
	}
}

Invoice.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.invoiceNumber.set(aJSONObject.get("number"));
	this.invoiceDate.setDate(aJSONObject.get("date"));
	this.validDate.setDate(aJSONObject.get("validDate"));
	this.customerName.set(aJSONObject.get("customerName"));
	this.customer = new Customer();
	this.customer.parseJSON(aJSONObject.get("customer"));
	this.href.set(aJSONObject.get("href"));

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

Invoice.prototype.isEditable = function() {
	if (typeof ($profile) == 'undefined') {
		return false;
	}
	if (this.validDate.getDate() != null) {
		return false;
	}

	return true;
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

Invoice.prototype.updateInvoiceLine = function(aIndex, aProduct) {
	iInvoiceLine = this.invoiceLines.get(aIndex);
	iInvoiceLine.sellingPrice.set(aProduct.sellingPrice.get());
	iInvoiceLine.tvaRate.set(aProduct.tva.rate.get())
	iInvoiceLine.tva = aProduct.tva;

	this.updateTotals();
}

Invoice.prototype.removeInvoiceLine = function(aIndex) {
	this.invoiceLines.remove(aIndex);

	this.updateTotals();
}

Invoice.prototype.removeInvoicePaymentLine = function(aIndex) {
	this.invoicePaymentLines.remove(aIndex);
}

Invoice.prototype.getTabTitle = function() {
	return "Facture n°" + this.invoiceNumber.get();
}

Invoice.prototype.print = function() {
	$http.ajax({
		url : "@maohifx.server/webapi/invoice/pdf?uuid=" + this.uuid.get(),
		type : "get",
		success : function($result, $status) {
			print($result);
			print($status);
			java.lang.Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + $result);
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
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

/**
 * Persist data
 * 
 * @param onSucces
 *            Executed when save succes
 * @param onError
 *            Executed when save error
 */
Invoice.prototype.save = function(onSucces, onError) {
	if (this.isValid()) {
		$http.ajax({
			source : this,
			url : "@maohifx.server/webapi/invoice",
			type : "post",
			contentType : "application/x-www-form-urlencoded",
			dataType : "application/json",
			data : this.toJSON(),
			success : function($result, $status) {
				this.source.parseJSON($result);

				alert("Save success!");

				if (typeof (onSucces) != 'undefined') {
					onSucces.run()
				}
			},
			error : function($error, $status) {
				error("Erreur lors de l'enregistrement de la facture", $error, $status);

				if (typeof (onError) != 'undefined') {
					onError.run()
				}
			}
		});
	}
}

Invoice.prototype.isValid = function() {
	if (this.customerName.get() == null) {
		alert("Le nom de client est vide");
		return false;
	}

	if (this.customer == null) {
		if (!this.customerName.get().isEmpty()) {
			alert("Le nom de client ne correspond à aucun client");
			return false;
		} else {
			alert("Veuillez sélectionner un client");
			return false;
		}
	}

	return true;
}
