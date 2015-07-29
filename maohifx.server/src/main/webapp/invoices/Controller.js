load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

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
	$loader.getNamespace().put("$data", this.data);
	this.data.clear();
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/invoices",
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Invoice.js");
			load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");

			for ( var item in $result) {
				iInvoice = new Invoice();
				iInvoice.parseJSON($result[item]);
				$data.add(iInvoice);
			}
		},
		error : function($result, $status) {
			java.lang.System.err.println($status);
		}
	});
}

InvoicesController.prototype.newInvoiceEvent = function(aEvent) {
	iLoader = $loader.getLoader();
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

InvoicesController.prototype.editInvoicesEvent = function(aEvent) {
	iInvoice = tableView.getSelectionModel().getSelectedItem();

	iLoader = $loader.getLoader();
	iLoader.getNamespace().put("$invoice", iInvoice);
	iLoader.load($tabpane, aEvent.getSource().getHref());
}

InvoicesController.prototype.keyTypeEvent = function(aEvent) {
	print("keyType");
	print(aEvent);
}
