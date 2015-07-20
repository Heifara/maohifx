load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var FXMLLoader = javafx.fxml.FXMLLoader;
var URL = java.net.URL;
var String = java.lang.String;

function open(aEvent) {
	iButton = aEvent.getSource();
	newTab(iButton.getId());
}

function testJSONEvent(aEvent) {
	iResponse = post();
	print(iResponse.getStatus());
	print(iResponse.readEntity(String.class));
}
