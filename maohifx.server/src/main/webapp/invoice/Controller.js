load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");
load("http://localhost:8080/maohifx.server/bean/InvoicePaymentLine.js");
load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Customer.js");

function InvoiceController() {
	this.autoCompletedProduct = null;
	this.autoCompletedCustomer = null;

	this.invoice = new Invoice();

	if (typeof ($item) != "undefined") {
		this.invoice.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.setText(this.invoice.getTabTitle());
	}

	this.addInvoiceLineEvent();
	this.addInvoicePaymentLineEvent();

	this.invoice.updateTotals();

	invoiceNumber.textProperty().bindBidirectional(this.invoice.invoiceNumber, new NumberStringConverter());
	invoiceDate.valueProperty().bindBidirectional(this.invoice.invoiceDate);
	customerName.textProperty().bindBidirectional(this.invoice.customerName);
	totalWithNoTaxes.textProperty().bindBidirectional(this.invoice.totalWithNoTaxes, new JSObjectStringConverter());
	totalTva.textProperty().bindBidirectional(this.invoice.totalTva, new JSObjectStringConverter());
	totalDiscount.textProperty().bindBidirectional(this.invoice.totalDiscount, new JSObjectStringConverter());
	totalWithTaxes.textProperty().bindBidirectional(this.invoice.totalWithTaxes, new JSObjectStringConverter());
	totalChange.textProperty().bindBidirectional(this.invoice.totalChange, new JSObjectStringConverter());

	invoiceLines.setItems(this.invoice.invoiceLines);
	invoiceLines.setContextMenu(new ContextMenu());
	iRemoveInvoiceLineMenuItem = new MenuItem("Supprimer");
	iRemoveInvoiceLineMenuItem.onAction = new javafx.event.EventHandler({
		handle : function(aActionEvent) {
			controller.removeInvoiceLineEvent(aActionEvent);
		}
	});
	invoiceLines.getContextMenu().getItems().add(iRemoveInvoiceLineMenuItem);

	invoicePaymentLines.setItems(this.invoice.invoicePaymentLines);

	invoicePaymentLines.setContextMenu(new ContextMenu());

	iRemoveInvoicePaymentLineMenuItem = new MenuItem("Supprimer");
	iRemoveInvoicePaymentLineMenuItem.onAction = new javafx.event.EventHandler({
		handle : function(aActionEvent) {
			controller.removeInvoicePaymentLineEvent();
		}
	});
	invoicePaymentLines.getContextMenu().getItems().add(iRemoveInvoicePaymentLineMenuItem);

	autoCompletion.addAll(Product.search());
	customerAutoCompletion.addAll(Customer.search());
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

InvoiceController.prototype.customerNameAutoCompletionEvent = function(aAutoCompletionEvent) {
	this.autoCompletedCustomer = aAutoCompletionEvent.getCompletion();
	System.out.println(this.autoCompletedCustomer);
}

InvoiceController.prototype.labelAutoCompletionEvent = function(aAutoCompletionEvent) {
	this.autoCompletedProduct = aAutoCompletionEvent.getCompletion();
}

InvoiceController.prototype.labelEditCommitEvent = function(aCellEditEvent) {
	iTablePosition = aCellEditEvent.getTablePosition();
	this.invoice.updateInvoiceLine(iTablePosition.getRow(), this.autoCompletedProduct);
	this.addInvoiceLineEvent();
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

	this.addInvoiceLineEvent();
	this.addInvoicePaymentLineEvent();
}

InvoiceController.prototype.printEvent = function() {
	this.invoice.print();
}

InvoiceController.prototype.removeInvoicePaymentLineEvent = function(aEvent) {
	iIndex = invoicePaymentLines.getSelectionModel().getSelectedIndex();
	if (iIndex > -1) {
		this.invoice.removeInvoicePaymentLine(iIndex);
	}

	this.addInvoicePaymentLineEvent(aEvent);
}

InvoiceController.prototype.sellingPriceEditable = function() {
	return true;
}

InvoiceController.prototype.removeInvoiceLineEvent = function(aEvent) {
	iIndex = invoiceLines.getSelectionModel().getSelectedIndex();
	if (iIndex > -1) {
		this.invoice.removeInvoiceLine(iIndex);
	}

	this.addInvoiceLineEvent(aEvent);
}