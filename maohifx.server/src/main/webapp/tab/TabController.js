load("http://localhost:8080/maohifx.server/common.js");

function TabController() {
	url.setText($url);
	this.refreshEvent();
};

TabController.prototype.newTabEvent = function(aEvent) {
	ExtFXMLLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}

TabController.prototype.newInvoiceEvent = function(aEvent) {
	ExtFXMLLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

TabController.prototype.homeEvent = function(aEvent) {
	ExtFXMLLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}

TabController.prototype.aboutEvent = function(aEvent) {
	ExtFXMLLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=about");
}

TabController.prototype.closeTabEvent = function(aEvent) {
	$tabpane.getTabs().remove($tabpane.getSelectionModel().getSelectedItem());
	if ($tabpane.getTabs().size() == 0) {
		java.lang.System.exit(0);
	}
}

TabController.prototype.refreshEvent = function(aEvent) {
	iLoader = ExtFXMLLoader.getLoader(url.getText());
	iLoader.getNamespace().put("$tab", $tab);
	iLoader.getNamespace().put("$toolbar", toolbar);
	content.setCenter(iLoader.load());
}

TabController.prototype.applyCaspienEvent = function(aEvent) {
	Application.setUserAgentStylesheet("CASPIAN");
}

TabController.prototype.applyModenaEvent = function(aEvent) {
	Application.setUserAgentStylesheet("MODENA");
}

TabController.prototype.hideShowAddressPaneEvent = function(aEvent) {
	if (addressPane.isVisible()) {
		addressPane.setVisible(false);
		topVBox.getChildren().remove(addressPane);
		showHideMenuItem.setText("Afficher la barre d'adresse");
	} else {
		addressPane.setVisible(true);
		topVBox.getChildren().add(addressPane);
		showHideMenuItem.setText("Masquer la barre d'adresse");
	}
	topVBox.autosize();
}