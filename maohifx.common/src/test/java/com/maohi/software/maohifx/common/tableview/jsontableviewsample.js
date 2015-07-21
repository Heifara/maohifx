load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var SimpleJSONProperty = com.maohi.software.maohifx.common.tableview.JSONItem;
var Date = java.util.Date;

function Controller() {
	this.data = FXCollections.observableArrayList();

	iData = new SimpleJSONProperty({
		birthdate : new Date(),
		firstname : "Toto",
		child : [ {
			firstname : "tutu"
		}, {
			firstname : "titi"
		} ]
	});

	this.data.add(iData);
	tableView.setItems(this.data);
}
