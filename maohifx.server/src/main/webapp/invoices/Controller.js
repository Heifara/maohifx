load("http://localhost:8080/maohifx.server/common.js");

function InvoicesController() {
	$tab.setText("Factures");

	this.data = FXCollections.observableArrayList();
	this.data.add(new JSONItem());
	this.data.add(new JSONItem());
	this.data.add(new JSONItem());
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
}

InvoicesController.prototype.newInvoiceEvent = function(aEvent) {
	ExtFXMLLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

InvoicesController.prototype.editInvoicesEvent = function(aEvent) {
}

InvoicesController.prototype.keyTypeEvent = function(aEvent) {
	print("keyType");
	print(aEvent);
}
