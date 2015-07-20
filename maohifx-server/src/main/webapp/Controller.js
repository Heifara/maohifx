load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var ClientBuilder = javax.ws.rs.client.ClientBuilder;
var Entity = javax.ws.rs.client.Entity;
var MediaType = javax.ws.rs.core.MediaType;

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
		iLoader.getNamespace().put("menuBar", menuBar);
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

function homeEvent(aEvent) {
	url.setText("http://localhost:8080/maohifx-server/webapi/fxml?id=home");
	refresh();
}

function aboutEvent(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=about");
}

function hideShowMenuBarEvent(aEvent) {
	if (menuBar.isVisible()) {
		menuBar.setVisible(false);
		hideShowMenuBarMenuItem.setText("Afficher la barre de menu");
	} else {
		menuBar.setVisible(true);
		hideShowMenuBarMenuItem.setText("Masquer la barre de menu");
	}
}

function post() {
	return ClientBuilder.newClient().target("http://localhost:8080/maohifx-server/webapi/post").request().post(Entity.entity(new String("Hello World"), MediaType.APPLICATION_JSON));
}