Product.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$http.ajax({
		url : "@maohifx.server/webapi/product/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			for ( var iItem in $result) {
				iElement = new Product();
				iElement.parseJSON($result[iItem]);
				iSearchResult.add(iElement);
			}
		},
		error : function($result, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
	return iSearchResult;
}

function Product() {
	this.uuid = new SimpleStringProperty();
	this.designation = new SimpleStringProperty();
	this.sellingPrice = new SimpleDoubleProperty();
	this.tva = new Tva();
	this.href = new SimpleStringProperty();

	// Calculated item see calc methods
	this.sellingPriceWithTaxes = new SimpleDoubleProperty();

	// Calculated item using bindings
	this.tvaAmount = Bindings.multiply(this.sellingPrice, this.tva.rate.divide(100));
}

Product.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		designation : this.designation.get(),
		sellingPrice : this.sellingPrice.get(),
		tva : this.tva.toJSON(),
	}
}

Product.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.designation.set(aJSONObject.get("designation"));
	this.sellingPrice.set(aJSONObject.get("sellingPrice"));
	this.tva.parseJSON(aJSONObject.get("tva"));
	this.href.set(aJSONObject.get("href"));

	this.sellingPriceWithTaxes.set(this.sellingPrice.multiply(this.tva.rate.divide(100).add(1)).get());
}

Product.prototype.parseTva = function(aTva) {
	this.tva = aTva;

	this.calcSellingPrice();
}

Product.prototype.parseProduct = function(aProduct) {
	this.uuid.set(null);
	this.designation.set(aProduct.designation.get());
	this.sellingPriceWithTaxes.set(aProduct.sellingPriceWithTaxes.get());
	this.parseTva(aProduct.tva);
}

Product.prototype.save = function() {
	$http.ajax({
		source : this,
		url : "@maohifx.server/webapi/product",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			this.source.parseJSON($result);

			alert("Save success");
		},
		error : function($result, $stackTrace) {
			error("Erreur lors de la sauvegarde", $error, $stackTrace);
		}
	});
}

Product.prototype.toString = function() {
	return this.designation.get();
}

Product.prototype.calcSellingPrice = function() {
	iSellingPrice = this.sellingPriceWithTaxes.divide(this.tva.rate.divide(100).add(1)).get()
	this.sellingPrice.set(iSellingPrice);
}