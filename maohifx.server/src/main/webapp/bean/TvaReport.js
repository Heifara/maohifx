TvaReport.search = function(aStart, aEnd) {
	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/invoice/tvaReport?start=" + aStart + "&end=" + aEnd,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			for ( var iItem in $result) {
				iElement = new TvaReport();
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

function TvaReport() {
	this.tvaRate = new SimpleDoubleProperty();
	this.amount = new SimpleDoubleProperty();

}

TvaReport.prototype.toJSON = function() {
	return {
		tvaRate : this.tvaRate.get(),
		amount : this.amount.get(),
	}
}

TvaReport.prototype.parseJSON = function(aJSONObject) {
	System.out.println(aJSONObject);
	this.tvaRate.set(aJSONObject.get("tvaRate"));
	this.amount.set(aJSONObject.get("amount"));
}

TvaReport.prototype.toString = function() {
	return this.tvaRate.get();
}