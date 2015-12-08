load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

function InvoiceController() {
	this.invoice = new Invoice();
	this.invoice.parseJSON($item);
	if (this.invoice.invoiceLines.size() == 0) {
		this.invoice.add("Saisir texte et ENTER");
	}

	invoiceNumber.textProperty().bindBidirectional(this.invoice.invoiceNumber, new NumberStringConverter());
	invoiceDate.valueProperty().bindBidirectional(this.invoice.invoiceDate);
	customerName.textProperty().bindBidirectional(this.invoice.customerName);

	tableView.setItems(this.invoice.invoiceLines);

	$tab.setText(this.invoice.getTabTitle());
}

InvoiceController.prototype.saveEvent = function() {
	this.invoice.save();
	$tab.setText(this.invoice.getTabTitle());
}

InvoiceController.prototype.printEvent = function() {
	this.invoice.print();
}

InvoiceController.prototype.deleteSelectedInvoiceLineEvent = function(aEvent) {
	iSelectedIndex = tableView.getSelectionModel().getSelectedIndex()
	this.invoice.remove(iSelectedIndex);
}