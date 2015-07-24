load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/invoice/Invoice.js");

function InvoicesController() {
	$tab.setText("Factures");

	this.data = FXCollections.observableArrayList();
	tableView.setItems(this.data);

	try {
		iRunnable = iAccelerators = tab.getContent().getScene().getAccelerators()
		iAccelerators.put(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), new java.lang.Runnable({
			run : function() {
				controller.newInvoiceEvent();
			}
		}));
		print(iAccelerators);
	} catch (e) {
	}
}

InvoicesController.prototype.searchEvent = function(aEvent) {
	this.data.clear();
	this.data.add(new Invoice());
}

InvoicesController.prototype.newInvoiceEvent = function(aEvent) {
	iLoader = $loader.getLoader();
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

InvoicesController.prototype.editInvoicesEvent = function(aEvent) {
	iInvoice = tableView.getSelectionModel().getSelectedItem();

	iLoader = $loader.getLoader();
	iLoader.getNamespace().put("$invoice", iInvoice);
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

InvoicesController.prototype.keyTypeEvent = function(aEvent) {
	print("keyType");
	print(aEvent);
}
