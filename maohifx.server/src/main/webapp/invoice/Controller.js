function InvoiceController() {
	this.autoCompletedProduct = null;

	this.invoice = new Invoice();

	if (typeof ($item) != "undefined") {
		this.invoice.parseJSON($item);
	}

	this.addInvoiceLineEvent();
	this.addInvoicePaymentLineEvent();

	this.invoice.updateTotals();

	invoiceNumber.textProperty().bindBidirectional(this.invoice.invoiceNumber, new NumberStringConverter());
	invoiceDate.valueProperty().bindBidirectional(this.invoice.invoiceDate);
	customerName.textProperty().bindBidirectional(this.invoice.customer, new ExtStringConverter());
	salesman.textProperty().bindBidirectional(this.invoice.salesman, new ExtStringConverter());
	totalWithNoTaxes.textProperty().bindBidirectional(this.invoice.totalWithNoTaxes, new ExtStringConverter());
	totalTva.textProperty().bindBidirectional(this.invoice.totalTva, new ExtStringConverter());
	totalDiscount.textProperty().bindBidirectional(this.invoice.totalDiscount, new ExtStringConverter());
	totalWithTaxes.textProperty().bindBidirectional(this.invoice.totalWithTaxes, new ExtStringConverter());
	totalChange.textProperty().bindBidirectional(this.invoice.totalChange, new ExtStringConverter());

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

	Product.search(autoCompletion);
	Customer.search(customerAutoCompletion);
	PaymentMode.search(paymentModeAutoCompletion);
	Salesman.search(salesmanAutoCompletion);

	this.fireEditable();

	this.onSucces = new Runnable({
		run : function() {
			controller.addInvoiceLineEvent();
			controller.addInvoicePaymentLineEvent();

			controller.fireEditable();
		}
	})
}

InvoiceController.prototype.packagingCodeCommitEvent = function(aCellActionEvent) {
	var iProductPackaging = aCellActionEvent.getItem();
	if (iProductPackaging != '' && iProductPackaging != null) {
		var iInvoiceLine = invoiceLines.getItems().get(aCellActionEvent.getTableRow().getIndex());
		if (iInvoiceLine != null) {
			if (iProductPackaging != null) {
				iInvoiceLine.parseProductPackaging(iProductPackaging);
			}
		}
	}
}

InvoiceController.prototype.packagingCodeUpdateItem = function(aCellActionEvent) {
	var iInvoiceLine = invoiceLines.getItems().get(aCellActionEvent.getTableRow().getIndex());
	if (iInvoiceLine != null) {
		console.log(iInvoiceLine.productPackagings);

		iComboBoxTableCell = aCellActionEvent.getSource();

		iComboBox = iComboBoxTableCell.getComboBox();
		iComboBox.getItems().clear();

		ProductPackaging.search(iComboBox.getItems(), iInvoiceLine.packaging.productUuid.get());
		iComboBox.getItems().addAll(iInvoiceLine.productPackagings);
		iComboBox.getSelectionModel().select(iInvoiceLine.packaging);
	}
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
	this.invoice.customer.set(aAutoCompletionEvent.getCompletion());
}

InvoiceController.prototype.salesmanAutoCompletionEvent = function(aAutoCompletionEvent) {
	this.invoice.salesman.set(aAutoCompletionEvent.getCompletion());
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
	if (iInvoicePaymentLine != null && iInvoicePaymentLine.mode.get().isEmpty()) {
		this.invoice.removeLastInvoicePaymentLine();
	}

	this.invoice.save(this.onSucces);
}

InvoiceController.prototype.validEvent = function() {
	if (this.invoice.isValid()) {
		iInvoiceLine = this.invoice.getLastInvoiceLine();
		if (iInvoiceLine.label.get().isEmpty()) {
			this.invoice.removeLastInvoiceLine();
		}

		iInvoicePaymentLine = this.invoice.getLastInvoicePaymentLine();
		if (iInvoicePaymentLine != null && iInvoicePaymentLine.mode.get().isEmpty()) {
			this.invoice.removeLastInvoicePaymentLine();
		}

		this.invoice.valid(this.onSucces);
	}
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

InvoiceController.prototype.fireEditable = function() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Facture n°" + this.invoice.invoiceNumber.get());
	}

	if (!this.invoice.isEditable()) {
		disableControls(view);

		// Handle exceptions
		printButton.setDisable(false);
	}
}

InvoiceController.prototype.removeInvoiceLineEvent = function(aEvent) {
	iIndex = invoiceLines.getSelectionModel().getSelectedIndex();
	if (iIndex > -1) {
		this.invoice.removeInvoiceLine(iIndex);
	}

	this.addInvoiceLineEvent(aEvent);
}