load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var FXMLLoader = javafx.fxml.FXMLLoader;
var URL = java.net.URL;

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
	var iNewTab = new Tab();
	iNewTab.setText("Nouvelle Onglet");
	iNewTab.setContent(loadUrl("http://localhost:8080/maohifx-server/webapi/fxml?id=newTab", aUrl));

	tabpane.getTabs().add(iNewTab);
	tabpane.getSelectionModel().select(tabpane.getTabs().indexOf(iNewTab));
}

/**
 * Using the FXMLLoader, load the url and put items
 * 
 * @param aUrl
 *            the url
 */
function loadUrl(aUrl, aDefaultUrl) {
	try {
		iLoader = new FXMLLoader(new URL(aUrl));
		iLoader.getNamespace().put("mainPane", mainPane);
		iLoader.getNamespace().put("tabpane", tabpane);
		iLoader.getNamespace().put("defaultUrl", aDefaultUrl);
		return iLoader.load();
	} catch (e) {
		print(e.getMessage());
	}
}

/**
 * Close the current tab
 */
function closeCurrentTabEvent(aEvent) {
	tabpane.getTabs().remove(tabpane.getTabs().indexOf(tabpane.getSelectionModel().getSelectedItem()))
	if (tabpane.getTabs().size() == 0) {
		java.lang.System.exit(0);
	}
}

function refreshCurrentTabEvent(event) {
	iCurrentTab = tabpane.getSelectionModel().getSelectedItem();
	url = iCurrentTab.getContent().getScene().lookup("#url");
	content = iCurrentTab.getContent().getScene().lookup("#content");
	if (!url.getText().isEmpty()) {
		content.setCenter(loadUrl(url.getText(), ""));
	}
}

/**
 * @param aEvent
 */
function refreshEvent(aEvent) {
	refresh();
}

function refresh() {
	if (!url.getText().isEmpty()) {
		content.setCenter(loadUrl(url.getText(), ""));
	}
}