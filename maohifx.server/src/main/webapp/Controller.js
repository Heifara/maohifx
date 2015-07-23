load("http://localhost:8080/maohifx.server/common.js");

MainController = function() {
	statusBar.setText("");
	statusBar.getLeftItems().clear();
	statusBar.getLeftItems().add(new Label("Welcome"));
	statusBar.getLeftItems().add(new Separator(Orientation.VERTICAL));
	statusBar.getLeftItems().add(new Label("Server: localhost:8080/maohifx.server/"));

	newTab("");

	print(java.lang.System.identityHashCode(this));
}
