load("http://localhost:8080/maohifx.server/common.js");

function HomeController() {
	$tab.setText("Home");

	toolbar.getItems().clear()

	iButton = new Button("Get");
	iButton.onAction = function() {
		$http.get("http://localhost:8080/maohifx.server/webapi/get");
	}
	toolbar.getItems().add(iButton);

	iResult = null;
	iButton = new Button("Get JSON");
	iButton.onAction = function() {
		$http.ajax({
			url : "http://localhost:8080/maohifx.server/webapi/get",
			type : "get",
			contentType : "application/x-www-form-urlencoded",
			dataType : "application/json",
			success : function($result, $status) {
				print($status);
				print($result);
				$tab.setText("Get sccess");
			},
		});
	}
	toolbar.getItems().add(iButton);

	iButton = new Button("Post");
	iButton.onAction = function() {
		$http.post("http://localhost:8080/maohifx.server/webapi/post");
	}
	toolbar.getItems().add(iButton);

	iButton = new Button("Post JSON");
	iButton.onAction = function() {
		$http.ajax({
			url : "http://localhost:8080/maohifx.server/webapi/get",
			type : "get",
			contentType : "application/x-www-form-urlencoded",
			dataType : "application/json",
			success : function($result, $status) {
				print($status);
				print($result);
				$tab.setText("Get sccess");
			},
		});
	}
	toolbar.getItems().add(iButton);

	iButton = new Button("JSON Error");
	iButton.onAction = function() {
		$http.ajax({
			url : "http://localhost:8080/maohifx.server/webapi/unknown",
			type : "get",
			contentType : "application/x-www-form-urlencoded",
			dataType : "application/json",
			error : function($result, $status) {
				print($status);
				print($result);
				$tab.setText("Get Erreur");
			},
		});
	}
	toolbar.getItems().add(iButton);
}

HomeController.prototype.openEvent = function(aEvent) {
	iButton = aEvent.getSource();
	iLoader = $loader.getLoader();
	iLoader.load($tabpane, iButton.getId());
}
