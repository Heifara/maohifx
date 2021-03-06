Tva.search = function(aCollection, aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	$http.ajax({
		url : "@maohifx.server/webapi/tva/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new Tva();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
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
