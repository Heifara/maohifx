load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");
load("http://localhost:8080/maohifx.server/bean/InvoicePaymentLine.js");
load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");

function InvoiceController() {
	this.autoCompletedProduct = null;

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

	this.addAutoCompletion("TOUQUE DE PEINTURE BLEU 25L");
	this.addAutoCompletion("TOUQUE DE PEINTURE ROUGE 25L");
	this.addAutoCompletion("TOUQUE DE PEINTURE JAUNE 25L");
	this.addAutoCompletion("TOUQUE DE PEINTURE VERT 25L");
	this.addAutoCompletion("TOUQUE DE PEINTURE ROSE 25L");
	this.addAutoCompletion("TOUQUE DE PEINTURE BLANC 25L");
	this.addAutoCompletion("PVC BLANC");
	this.addAutoCompletion("PVC BLEU");
	this.addAutoCompletion("PVC ROUGE");
	this.addAutoCompletion("PVC JAUNE");
}

InvoiceController.prototype.addAutoCompletion = function(aDesignation) {
	iProduct = new Product();
	iProduct.designation.set(aDesignation);
	iProduct.sellingPrice.set(15000.0);
	iProduct.tvaRate.set(16.0);
	autoCompletion.add(iProduct);
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