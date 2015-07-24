load("http://localhost:8080/maohifx.server/common.js");

var refreshing = false;

/**
 * Control the tab
 */
function TabController() {
	$loader.getNamespace().put("$toolbar", toolbar);
	$loader.getNamespace().put("$tab", $tab);

	statusBar.setText("");

	this.urlStatus = new Label();
	this.urlStatus.textProperty().bindBidirectional(urlField.textProperty());
	statusBar.getLeftItems().add(this.urlStatus)

	urlField.setText($url);
	this.refreshEvent();
	this.hideShowAddressPaneEvent();
};

TabController.prototype.refreshing = false;

TabController.prototype.newTabEvent = function(aEvent) {
	iLoader = $loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab");
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}

TabController.prototype.newInvoiceEvent = function(aEvent) {
	iLoader = $loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab");
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=invoice");
}

TabController.prototype.homeEvent = function(aEvent) {
	iLoader = $loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab");
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}

TabController.prototype.aboutEvent = function(aEvent) {
	iLoader = $loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab");
	iLoader.load($tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=about");
}

TabController.prototype.closeTabEvent = function(aEvent) {
	$tabpane.getTabs().remove($tabpane.getSelectionModel().getSelectedItem());
	if ($tabpane.getTabs().size() == 0) {
		java.lang.System.exit(0);
	}
}

TabController.prototype.refreshEvent = function(aEvent) {
	if (!refreshing) {
		refreshing = true;

		content.setCenter(null);

		iProgressBar = new ProgressIndicator();
		statusBar.getRightItems().add(iProgressBar);

		new Thread(new Task({
			call : function() {
				Platform.runLater(new Runnable({
					run : function() {
						content.setCenter($loader.getLoader(urlField.getText()).load());
						statusBar.getRightItems().remove(iProgressBar);
						refreshing = false;
					}
				}));
			}
		})).start();
	}
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