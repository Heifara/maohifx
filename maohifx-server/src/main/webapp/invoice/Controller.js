load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var JSONItem = com.maohi.software.maohifx.common.tableview.JSONItem;

function Controller() {
	this.data = FXCollections.observableArrayList();

	this.data.add(new JSONItem({
		position : 0,
		barcode : "65165161261",
		label : "1ère essaie de ligne de facture",
		quantity : 4.0,
		sellingPrice : 1500.0,
		discount : 10
	}));
	this.data.add(new JSONItem({
		position : 1,
		barcode : "91919475613",
		label : "2ème essaie de ligne de facture",
		quantity : 10.0
	}));

	tableView.setItems(this.data);
}

Controller.prototype.saveEvent = function() {
	print("saved");
}

Controller.prototype.printEvent = function() {
	print("printed");
}
