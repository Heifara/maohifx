load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var JSONItem = com.maohi.software.samples.tableview.json.JSONItem;
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
