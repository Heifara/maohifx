load("http://localhost:8080/maohifx.server/common.js");

MainController = function() {
	ExtFXMLLoader.getGlobalNamespace().put("$mainPane", mainPane);
	ExtFXMLLoader.getGlobalNamespace().put("$tabpane", tabpane);
	ExtFXMLLoader.getGlobalNamespace().put("$statusBar", statusBar);
	ExtFXMLLoader.load(tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}
