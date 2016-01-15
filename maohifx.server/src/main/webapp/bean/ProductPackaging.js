ProductPackaging.search = function(aCollection, aProductUuid) {
	$http.ajax({
		url : "@maohifx.server/webapi/generic/ProductPackaging?action=getAll&where=id.productUuid='" + aProductUuid + "'",
		type : "get",
		data : aProductUuid,
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new ProductPackaging();
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

function ProductPackaging() {
	this.productUuid = new SimpleStringProperty("");
	this.packagingCode = new SimpleStringProperty("");

	this.packaging = null;

	this.main = new SimpleBooleanProperty(false);
	this.sellingPrice = new SimpleDoubleProperty(0.0);

}

ProductPackaging.prototype.getId = function() {
	return {
		productUuid : this.productUuid.get(),
		packagingCode : this.packagingCode.get(),
	}
}

ProductPackaging.prototype.toJSON = function() {
	return {
		id : this.getId(),
		packaging : this.packaging.toJSON(),
		main : this.main.get(),
		sellingPrice : this.sellingPrice.get(),
	};
}

ProductPackaging.prototype.parseJSON = function(aJSONObject) {
	this.productUuid.set(aJSONObject.get("id").get("productUuid"));
	this.packagingCode.set(aJSONObject.get("id").get("packagingCode"));

	this.packaging = new Packaging();
	this.packaging.parseJSON(aJSONObject.get("packaging"));

	this.main.set(aJSONObject.get("main"));
	this.sellingPrice.set(aJSONObject.get("sellingPrice"));

	// TODO Parse ProductPackagingBarcodes
}

ProductPackaging.prototype.toString = function() {
	return this.packagingCode.get();
}

ProductPackaging.prototype.showLog = function() {
	iShowLog = new StringBuilder();
	iShowLog.append("ProductPackaging[");
	iShowLog.append("productUuid: " + this.productUuid);
	iShowLog.append(", packagingCode: " + this.packagingCode);
	iShowLog.append(", main: " + this.main);
	iShowLog.append(", sellingPrice: " + this.sellingPrice);
	iShowLog.append("]");
	System.out.println(iShowLog.toString());
}
