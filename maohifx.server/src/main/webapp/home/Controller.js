load("http://localhost:8080/maohifx.server/common.js");

function HomeController() {
	$tab.setText("Home");
}

HomeController.prototype.openEvent = function(aEvent) {
	iButton = aEvent.getSource();
	openTab(iButton.getId());
}
