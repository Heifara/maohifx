load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/TabManager.js");

MainController = function() {
	this.tabManager = new TabManager();
	this.tabManager.newTab("http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}
