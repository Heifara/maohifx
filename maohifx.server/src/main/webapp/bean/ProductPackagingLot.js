ProductPackagingLot.search = function(aCollection, aPackagingCode, aProductUuid) {

	console.log("search");

	var iUrl = "@maohifx.server/webapi/generic/ProductPackagingLot?action=getAll&where=id.productPackagingPackagingCode='" + aPackagingCode + "'+AND+id.productPackagingProductUuid='" + aProductUuid + "'";

	$http.ajax({
		url : iUrl,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new ProductPackagingLot();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
}

function ProductPackagingLot() {
	this.lot = new SimpleIntegerProperty();
	this.productPackagingPackagingCode = new SimpleStringProperty();
	this.productPackagingProductUuid = new SimpleStringProperty();

	this.productPackaging = new SimpleObjectProperty();
	this.costPrice = new SimpleDoubleProperty();
	this.weightedAverageCostPrice = new SimpleDoubleProperty();
	this.bestBefore = new SimpleLocalDateProperty();
}

ProductPackagingLot.prototype.toJSON = function(aObject) {
	if (typeof (aObject) == 'undefined') {
		return {
			id : {
				lot : this.lot.get(),
				productPackagingPackagingCode : this.productPackagingPackagingCode.get(),
				productPackagingProductUuid : this.productPackagingProductUuid.get(),
			},
			productPackaging : this.toJSON(this.productPackaging.get()),
			costPrice : this.costPrice.get(),
			weightedAverageCostPrice : this.weightedAverageCostPrice.get(),
			bestBefore : this.bestBefore.get(),
		}
	} else if (aObject != null) {
		return aObject.toJSON();
	}
}

ProductPackagingLot.prototype.parseJSON = function(aJSONObject) {
	this.lot.set(aJSONObject.get("id").get("lot"));
	this.productPackagingPackagingCode.set(aJSONObject.get("id").get("productPackagingPackagingCode"));
	this.productPackagingProductUuid.set(aJSONObject.get("id").get("productPackagingProductUuid"));

	var iProductPackaging = new ProductPackaging();
	iProductPackaging.parseJSON(aJSONObject.get("productPackaging"));
	this.productPackaging.set(iProductPackaging);

	this.costPrice.set(aJSONObject.get("costPrice"));
	this.weightedAverageCostPrice.set(aJSONObject.get("weightedAverageCostPrice"));
	this.bestBefore.set(aJSONObject.get("bestBefore"));

	console.log(this.toJSON());
}

ProductPackagingLot.prototype.toString = function() {
	return this.lot.get().toString();
}