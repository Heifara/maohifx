ReportController.id = "sampleReport";
ReportController.title = "Etat Exemple";

function ReportController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText(ReportController.title);
		$tab.setIcon("@maohifx.server/" + ReportController.id + "/tab-icon.png") // must be 16x16
	}
}

ReportController.prototype.searchEvent = function() {
	results.getItems().clear();
	$http.ajax({
		url : "@maohifx.server/webapi/reports/" + ReportController.id,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			for ( var iItem in $result) {
				iElement = new ReportRow();
				iElement.parseJSON($result[iItem]);
				results.getItems().add(iElement);
			}
		},
		error : function($error, $status, $stackTrace) {
			error("Erreur durant la recherche", $error, "");
		}
	});
}

function ReportRow() {
	this.label = new SimpleStringProperty("");
	this.date = new SimpleStringProperty("");
}

ReportRow.prototype.toJSON = function() {
}

ReportRow.prototype.parseJSON = function(aJSONObject) {
	warn("Résultat de la recherche", aJSONObject)
	this.label.set(aJSONObject[0]);
	this.date.set(aJSONObject[1]);
}

ReportRow.prototype.toString = function() {
}