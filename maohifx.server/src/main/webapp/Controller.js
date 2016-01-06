function HomeController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Maison");
	}

	runLater(new Runnable({
		run : function() {
			
		}
	}));
}
