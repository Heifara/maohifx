Tva.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/tva/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Tva.js");

			for ( var iItem in $result) {
				iElement = new Tva();
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

function Tva() {
	this.type = new SimpleIntegerProperty(0);
	this.label = new SimpleStringProperty("");
	this.rate = new SimpleDoubleProperty(0.0);
}

Tva.prototype.toJSON = function() {
	return {
		type : this.type.get(),
		label : this.label.get(),
		rate : this.rate.get(),
	}
}

Tva.prototype.parseJSON = function(aJSONObject) {
	this.type.set(aJSONObject.get("type"));
	this.label.set(aJSONObject.get("label"));
	this.rate.set(aJSONObject.get("rate"));
}

Tva.prototype.toString = function() {
	return this.type.get() + " - " + this.label.get() + " " + this.rate.get() + "%";
}
