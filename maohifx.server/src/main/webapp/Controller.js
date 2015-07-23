load("http://localhost:8080/maohifx.server/common.js");

MainController = function() {
	$loader.getNamespace().put("$mainPane", mainPane);
	$loader.getNamespace().put("$tabpane", tabpane);
	$loader.getNamespace().put("$statusBar", statusBar);
	$loader.getLoader("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab").load(tabpane, "http://localhost:8080/maohifx.server/webapi/fxml?id=home");
}
