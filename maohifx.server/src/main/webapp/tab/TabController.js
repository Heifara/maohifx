load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/TabManager.js");

function TabController() {
	this.tabManager = new TabManager();
	
	url.setText(defaultUrl);
};

TabController.prototype.newTabEvent = function(aEvent) {
	this.tabManager.newTab("http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}

TabController.prototype.newInvoiceEvent = function(aEvent) {
	this.tabManager.newTab("http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

TabController.prototype.homeEvent = function(aEvent) {
	this.tabManager.newTab("http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}

TabController.prototype.aboutEvent = function(aEvent) {
	this.tabManager.newTab("http://localhost:8080/maohifx.server/webapi/fxml?id=about");
}

TabController.prototype.closeTabEvent = function(aEvent) {
	tabpane.getTabs().remove(tabpane.getTabs().indexOf(tabpane.getSelectionModel().getSelectedItem()))
	if (tabpane.getTabs().size() == 0) {
		java.lang.System.exit(0);
	}
}

TabController.prototype.refreshEvent = function(aEvent) {
	this.tabManager.refresh();
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