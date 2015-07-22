load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var FXMLLoader = javafx.fxml.FXMLLoader;
var URL = java.net.URL;

function init() {
	hideShowAddressPaneEvent();

	url.setText(defaultUrl);
	refresh();
}

function hideShowAddressPaneEvent(aEvent) {
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

/**
 * Open a new Tab to home
 * 
 * @param aEvent
 */
function newTabEvent(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=invoices");
}

function newInvoiceEvent(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=invoice");
}

/**
 * Open a new Tab
 * 
 * @param aEvent
 *            the event
 */
function newTab(aUrl) {
	try {
		var iNewTab = new Tab();

		iLoader = new FXMLLoader(new URL("http://localhost:8080/maohifx-server/webapi/fxml?id=newTab"));
		iLoader.getNamespace().put("mainPane", mainPane);
		iLoader.getNamespace().put("tabpane", tabpane);
		iLoader.getNamespace().put("defaultUrl", aUrl);
		iLoader.getNamespace().put("tab", iNewTab);

		iNewTab.setText("Nouvelle Onglet");
		iNewTab.setContent(iLoader.load());

		tabpane.getTabs().add(iNewTab);
		tabpane.getSelectionModel().select(tabpane.getTabs().indexOf(iNewTab));
	} catch (e) {
		print(e);
		print(e.getMessage());
	}
}

/**
 * Close the current tab
 */
function closeTabEvent(aEvent) {
	tabpane.getTabs().remove(tabpane.getTabs().indexOf(tabpane.getSelectionModel().getSelectedItem()))
	if (tabpane.getTabs().size() == 0) {
		java.lang.System.exit(0);
	}
}

function getNodeById(aId) {
	return tabpane.getSelectionModel().getSelectedItem().getContent().getScene().lookup(aId);
}

/**
 * @param aEvent
 */
function refreshEvent(aEvent) {
	refresh();
}

function refresh() {
	if (!url.getText().isEmpty()) {
		try {
			iLoader = new FXMLLoader(new URL(url.getText()));
			iLoader.getNamespace().put("mainPane", mainPane);
			iLoader.getNamespace().put("tabpane", tabpane);
			iLoader.getNamespace().put("tab", tab);
			iLoader.getNamespace().put("toolbar", toolbar);
			iLoader.getNamespace().put("menuButton", menuButton);

			content.setCenter(iLoader.load());
		} catch (e) {
			print(e);
			print(e.getMessage());
		}
	}
}