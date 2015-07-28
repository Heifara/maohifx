load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

function InvoiceController() {
	this.invoice = typeof ($invoice) == "undefined" ? new Invoice() : $invoice;
	this.invoice.add("Saisir un text ici et appuyer sur ENTER");
	
	invoiceNumber.setText(this.invoice.invoiceNumber.get());

	tableView.setItems(this.invoice.invoiceLines);
	
	$tab.setText(this.invoice.getTabTitle());
}

InvoiceController.prototype.saveEvent = function() {
	this.invoice.save();
	invoiceNumber.setText(this.invoice.invoiceNumber.get());
	$tab.setText(this.invoice.getTabTitle());
}

InvoiceController.prototype.printEvent = function() {
	this.invoice.print();
}

InvoiceController.prototype.updateDataEvent = function(aEvent) {
	iSource = aEvent.getSource();
	switch (iSource.getId()) {
		case "invoiceDate":
			this.invoice.invoiceDate.set(iSource.getValue());
			print(this.invoice.invoiceDate.getValue());
			break;

		default:
			break;
	}

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