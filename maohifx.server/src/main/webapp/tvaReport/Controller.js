load("http://localhost:8080/maohifx.server/common.js");

function TvaReportController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Déclaration TVA");
	}

	Platform.runLater(new Runnable({
		run : function() {
			start.requestFocus();
		}
	}));
}

TvaReportController.prototype.searchEvent = function(aEvent) {
	tvaReports.setItems(TvaReport.search(start.getValue(), end.getValue()));
}
