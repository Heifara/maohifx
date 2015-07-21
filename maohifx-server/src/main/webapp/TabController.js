load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var FXMLLoader = javafx.fxml.FXMLLoader;
var URL = java.net.URL;

function init() {
	url.setText(defaultUrl);
	refresh();
}

/**
 * Open a new Tab to home
 * 
 * @param aEvent
 */
function newTabEvent(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=home");
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
		iLoader = new FXMLLoader(new URL("http://localhost:8080/maohifx-server/webapi/fxml?id=newTab"));
		iLoader.getNamespace().put("mainPane", mainPane);
		iLoader.getNamespace().put("tabpane", tabpane);
		iLoader.getNamespace().put("defaultUrl", aUrl);

		var iNewTab = new Tab();
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
	print("refresh");
	if (!url.getText().isEmpty()) {
		try {
			iLoader = new FXMLLoader(new URL(defaultUrl));
			iLoader.getNamespace().put("mainPane", mainPane);
			iLoader.getNamespace().put("tabpane", tabpane);
			iLoader.getNamespace().put("toolbar", toolbar);
			iLoader.getNamespace().put("menuButton", menuButton);

			content.setCenter(iLoader.load());
		} catch (e) {
			print(e);
			print(e.getMessage());
		}
	}
}