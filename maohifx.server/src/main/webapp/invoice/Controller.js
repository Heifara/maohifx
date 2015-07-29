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
	print(this.invoice.invoiceDate.getDate());
}

InvoiceController.prototype.updateDataEvent = function(aEvent) {
	iSource = aEvent.getSource();
	switch (iSource.getId()) {
		case "invoiceDate":
			print(iSource.getValue());
			this.invoice.invoiceDate.set(iSource.getValue());
			break;
			
		case "customerName":
			print(iSource.getText());
			this.invoice.customerName.set(iSource.getText());
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