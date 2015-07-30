load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

function InvoiceController() {
	this.invoice = typeof ($invoice) == "undefined" ? new Invoice() : $invoice;
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

InvoiceController.prototype.onEditCommit = function(aEvent) {
	iCurrentRow = aEvent.getTablePosition().getRow();
	iInvoiceLine = this.invoice.invoiceLines.get(iCurrentRow);

	iSource = aEvent.getSource();
	switch (iSource.getId()) {
		case "label":
			iInvoiceLine.label.set(aEvent.getNewValue());
			break;

		default:
			break;
	}

	if (iCurrentRow == this.invoice.invoiceLines.size() - 1) {
		this.invoice.add("Saisir un text ici et appuyer sur ENTER");
	}
}

InvoiceController.prototype.deleteSelectedInvoiceLineEvent = function(aEvent) {
	iSelectedIndex = tableView.getSelectionModel().getSelectedIndex()
	this.invoice.remove(iSelectedIndex);
}