Packaging.search = function(aCollection) {
	$http.ajax({
		url : "@maohifx.server/webapi/Packaging/getAll",
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new Packaging();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $status, $stackTrace) {
			error("Erreur durant la recherche", $error, "");
			System.err.println($stackTrace);
		}
	});
}

function Packaging() {
	this.code = new SimpleStringProperty("");
}

Packaging.prototype.toJSON = function() {
	return {
		code : this.code.get(),
	};
}

Packaging.prototype.parseJSON = function(aJSONObject) {
	this.code.set(aJSONObject.get("code"));
}

Packaging.prototype.toString = function() {
	return this.code.get();
}
