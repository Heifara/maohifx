load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");
load("http://localhost:8080/maohifx.server/bean/InvoicePaymentLine.js");
load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");

function InvoiceController() {
	this.invoice = new Invoice();

	if (typeof ($item) != "undefined") {
		this.invoice.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.setText(this.invoice.getTabTitle());
	}

	this.addInvoiceLineEvent();
	this.addInvoicePaymentLineEvent();

	invoiceNumber.textProperty().bindBidirectional(this.invoice.invoiceNumber, new NumberStringConverter());
	invoiceDate.valueProperty().bindBidirectional(this.invoice.invoiceDate);
	customerName.textProperty().bindBidirectional(this.invoice.customerName);
	totalWithNoTaxes.textProperty().bindBidirectional(this.invoice.totalWithNoTaxes, new JSObjectStringConverter());
	totalTva.textProperty().bindBidirectional(this.invoice.totalTva, new JSObjectStringConverter());
	totalDiscount.textProperty().bindBidirectional(this.invoice.totalDiscount, new JSObjectStringConverter());
	totalWithTaxes.textProperty().bindBidirectional(this.invoice.totalWithTaxes, new JSObjectStringConverter());
	totalChange.textProperty().bindBidirectional(this.invoice.totalChange, new JSObjectStringConverter());

	invoiceLines.setItems(this.invoice.invoiceLines);
	invoicePaymentLines.setItems(this.invoice.invoicePaymentLines);
}

InvoiceController.prototype.addInvoiceLineEvent = function(aEvent) {
	iInvoiceLine = this.invoice.getLastInvoiceLine();
	if (iInvoiceLine == null) {
		this.invoice.addInvoiceLine();
	} else if (!iInvoiceLine.label.get().isEmpty()) {
		this.invoice.addInvoiceLine();
	}
}

InvoiceController.prototype.addInvoicePaymentLineEvent = function(aEvent) {
	iInvoicePaymentLine = this.invoice.getLastInvoicePaymentLine();
	if (iInvoicePaymentLine == null) {
		this.invoice.addInvoicePaymentLine();
	} else if (!iInvoicePaymentLine.mode.get().isEmpty()) {
		this.invoice.addInvoicePaymentLine();
	}
}

InvoiceController.prototype.updateInvoiceEvent = function(aEvent) {
	this.invoice.updateTotals();
}

InvoiceController.prototype.saveEvent = function() {
	iInvoiceLine = this.invoice.getLastInvoiceLine();
	if (iInvoiceLine.label.get().isEmpty()) {
		this.invoice.removeLastInvoiceLine();
	}

	iInvoicePaymentLine = this.invoice.getLastInvoicePaymentLine();
	if (iInvoicePaymentLine.mode.get().isEmpty()) {
		this.invoice.removeLastInvoicePaymentLine();
	}

	this.invoice.save();
}

InvoiceController.prototype.printEvent = function() {
	this.invoice.print();
}

/**
 * 
 * @param aEvent
 *            the CellActionEvent
 */
InvoiceController.prototype.deleteSelectedInvoiceLineEvent = function(aEvent) {
	this.invoice.remove(aEvent.getIndex());
	this.addItemEvent(aEvent);
}