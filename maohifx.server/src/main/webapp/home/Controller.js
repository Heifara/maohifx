load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/TabManager.js");

function HomeController() {
}

HomeController.prototype.openEvent = function(aEvent) {
	iTabManager = new TabManager();

	iButton = aEvent.getSource();
	iTabManager.newTab(iButton.getId());
}
