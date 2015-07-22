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

Controller.prototype.searchEvent = function(aEvent) {
	this.data.clear();
}

Controller.prototype.newInvoiceEvent = function(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=invoice");
}

Controller.prototype.editInvoicesEvent = function(aEvent) {
}

Controller.prototype.keyTypeEvent = function(aEvent) {
	print("keyType");
	print(aEvent);
}
