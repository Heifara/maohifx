load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");
load("http://localhost:8080/maohifx-server/TabController.js");
load("http://localhost:8080/maohifx-server/Controller.js");
var JSONItem = com.maohi.software.maohifx.common.tableview.JSONItem;

function Controller() {
	this.data = FXCollections.observableArrayList();
	this.data.add(new JSONItem());
	this.data.add(new JSONItem());
	this.data.add(new JSONItem());
	tableView.setItems(this.data);
}

Controller.prototype.searchEvent = function(aEvent) {
	this.data.clear();
}

Controller.prototype.newInvoiceEvent = function(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=invoice");
}

Controller.prototype.editInvoicesEvent = function(aEvent) {
}

