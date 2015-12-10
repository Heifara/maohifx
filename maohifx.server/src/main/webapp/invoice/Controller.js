load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

function test() {
	System.out.println("test")
}

function InvoiceController() {
	this.invoice = new Invoice();

	if (typeof ($tab) != 'undefined') {
		$tab.setText(this.invoice.getTabTitle());
	}

	if (typeof ($item) != "undefined") {
		this.invoice.parseJSON($item);
	}

	if (this.invoice.invoiceLines.size() == 0) {
		this.invoice.add();
	} else {
		this.addItemEvent();
	}

	invoiceNumber.textProperty().bindBidirectional(this.invoice.invoiceNumber, new NumberStringConverter());
	invoiceDate.valueProperty().bindBidirectional(this.invoice.invoiceDate);
	customerName.textProperty().bindBidirectional(this.invoice.customerName);
	totalWithNoTaxes.textProperty().bindBidirectional(this.invoice.totalWithNoTaxes, new JSObjectStringConverter());
	totalTva.textProperty().bindBidirectional(this.invoice.totalTva, new JSObjectStringConverter());
	totalDiscount.textProperty().bindBidirectional(this.invoice.totalDiscount, new JSObjectStringConverter());
	totalWithTaxes.textProperty().bindBidirectional(this.invoice.totalWithTaxes, new JSObjectStringConverter());
	totalChange.textProperty().bindBidirectional(this.invoice.totalChange, new JSObjectStringConverter());

	tableView.setItems(this.invoice.invoiceLines);
}

InvoiceController.prototype.addItemEvent = function(aEvent) {
	iInvoiceLine = this.invoice.getLastInvoiceLine();
	if (iInvoiceLine == null) {
		this.invoice.add();
	}
	if (!iInvoiceLine.label.get().isEmpty()) {
		this.invoice.add();
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

	this.invoice.save();

	this.addItemEvent();

	$tab.setText(this.invoice.getTabTitle());
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