load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var ExtFXMLLoader = com.maohi.software.maohifx.client.ExtFXMLLoader;

var Application = javafx.application.Application;
var FXMLLoader = javafx.fxml.FXMLLoader;
var URL = java.net.URL;

var JSONItem = com.maohi.software.maohifx.common.tableview.JSONItem;

var System = java.lang.System;

function openTab(aUrl) {
	iLoader = $loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab");
	iLoader.load($tabpane, aUrl);
}
