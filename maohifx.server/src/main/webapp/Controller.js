load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");

function HomeController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Maison");
	}

	runLater(new Runnable({
		run : function() {
			
		}
	}));
}
