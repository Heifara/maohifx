load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");
load("http://localhost:8080/maohifx.server/TabManager.js");

function HomeController() {
}

HomeController.prototype.openEvent = function(aEvent) {
	iTabManager = new TabManager();

	iButton = aEvent.getSource();
	iTabManager.newTab(iButton.getId());
}
