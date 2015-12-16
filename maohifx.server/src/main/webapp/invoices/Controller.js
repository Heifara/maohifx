load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");
load("http://localhost:8080/maohifx.server/bean/InvoicePaymentLine.js");
load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");

function InvoicesController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Factures");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));

	this.data = FXCollections.observableArrayList();
	tableView.setItems(this.data);
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
			load("http://localhost:8080/maohifx.server/bean/InvoicePaymentLine.js");
			load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");

			for ( var item in $result) {
				iInvoice = new Invoice();
				iInvoice.parseJSON($result[item]);
				$data.add(iInvoice);
			}
		},
		error : function($result, $status) {
			java.lang.System.err.println($result);
			java.lang.System.err.println($status);
		}
	});
}
