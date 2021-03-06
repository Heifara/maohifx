PaymentMode.search = function(aCollection, aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	$http.ajax({
		url : "@maohifx.server/webapi/paymentMode/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new PaymentMode();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
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