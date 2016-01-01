TvaReport.search = function(aCollection, aStart, aEnd) {
	var iUrl = "@maohifx.server/webapi/invoice/tvaReport"
	if (aStart != null && aEnd != null) {
		iUrl = "@maohifx.server/webapi/invoice/tvaReport?start=" + aStart + "&end=" + aEnd;
	} else if (aStart != null && aEnd == null) {
		iUrl = "@maohifx.server/webapi/invoice/tvaReport?start=" + aStart;
	} else if (aStart == null && aEnd != null) {
		iUrl = "@maohifx.server/webapi/invoice/tvaReport?end=" + aEnd;
	}

	$http.ajax({
		url : iUrl,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new TvaReport();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($result, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
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
	this.tvaRate.set(aJSONObject.get("tvaRate"));
	this.amount.set(aJSONObject.get("amount"));
}

TvaReport.prototype.toString = function() {
	return this.tvaRate.get();
}