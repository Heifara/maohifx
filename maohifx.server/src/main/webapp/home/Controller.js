load("http://localhost:8080/maohifx.server/common.js");

function HomeController() {
	$tab.setText("Home");

	iButton = new Button("Get");
	iButton.onAction = function() {
		iResponse = $http.get("http://localhost:8080/maohifx.server/webapi/get");
		print(iResponse);
	}
	toolbar.getItems().add(iButton);

	iButton = new Button("Get JSON");
	iButton.onAction = function() {
		iResponse = $http.get({
			url : "http://localhost:8080/maohifx.server/webapi/get",
			data : {
				value1 : "test",
				value2 : 2.2
			}
		});
		print(iResponse);
	}
	toolbar.getItems().add(iButton);

	iButton = new Button("Post");
	iButton.onAction = function() {
		iResponse = $http.post("http://localhost:8080/maohifx.server/webapi/post");
		print(iResponse);
	}
	toolbar.getItems().add(iButton);

	iButton = new Button("Post JSON");
	iButton.onAction = function() {
		iResponse = $http.get({
			url : "http://localhost:8080/maohifx.server/webapi/post",
			data : ""
		});
		print(iResponse);
	}
	toolbar.getItems().add(iButton);
}

HomeController.prototype.openEvent = function(aEvent) {
	iButton = aEvent.getSource();
	iLoader = $loader.getLoader();
	iLoader.load($tabpane, iButton.getId());
}
