PaymentMode.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/paymentMode/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");

			for ( var iItem in $result) {
				iElement = new PaymentMode();
				iElement.parseJSON($result[iItem]);
				$data.add(iElement);
			}
		},
		error : function($result, $status) {
			java.lang.System.err.println($result);
			java.lang.System.err.println($status);
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