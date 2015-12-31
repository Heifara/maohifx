PaymentMode.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$http.ajax({
		url : "@maohifx.server/webapi/paymentMode/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			for ( var iItem in $result) {
				iElement = new PaymentMode();
				iElement.parseJSON($result[iItem]);
				iSearchResult.add(iElement);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
	return iSearchResult;
}

function PaymentMode() {
	this.id = new SimpleIntegerProperty();
	this.label = new SimpleStringProperty();
}

PaymentMode.prototype.toJSON = function() {
	return {
		id : this.id.get(),
		label : this.label.get(),
	};
}

PaymentMode.prototype.parseJSON = function(aJSONObject) {
	this.id.set(aJSONObject.get("id"));
	this.label.set(aJSONObject.get("label"));
}

PaymentMode.prototype.toString = function() {
	return this.label.get();
}

PaymentMode.prototype.toConsole = function() {
	print(this.toString());
}