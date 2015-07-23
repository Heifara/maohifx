load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");
load("http://localhost:8080/maohifx.server/TabController.js");
load("http://localhost:8080/maohifx.server/Controller.js");

var JSONItem = com.maohi.software.maohifx.common.tableview.JSONItem;
var String = java.lang.String;

function open(aEvent) {
	iButton = aEvent.getSource();
	newTab(iButton.getId());
}

function testJSONEvent(aEvent) {
	iResponse = post();
	iPerson = iResponse.readEntity(String.class)

	iData = FXCollections.observableArrayList();
	iData.add(new JSONItem(iPerson));

	tableView.setItems(iData);
}
