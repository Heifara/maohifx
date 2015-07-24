load("http://localhost:8080/maohifx.server/common.js");

function InvoiceController() {
	this.data = FXCollections.observableArrayList();

	this.data.add({
		position : 0,
		barcode : "65165161261",
		label : "1ère essaie de ligne de facture",
		quantity : 4.0,
		sellingPrice : 1500.0,
		discount : 10
	});
	this.data.add({
		position : 1,
		barcode : "91919475613",
		label : "2ème essaie de ligne de facture",
		quantity : 10.0
	});

	tableView.setItems(this.data);
}

InvoiceController.prototype.saveEvent = function() {
	print("saved");
}

InvoiceController.prototype.printEvent = function() {
	print("printed");
}
