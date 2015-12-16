Product.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/product/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Product.js");

			for ( var iItem in $result) {
				iElement = new Product();
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

function Product() {
	this.uuid = new SimpleStringProperty();
	this.designation = new SimpleStringProperty();
	this.sellingPrice = new SimpleDoubleProperty();
	this.tvaRate = new SimpleDoubleProperty();
	this.href = new SimpleStringProperty();

	// Calculated item see calc methods
	this.sellingPriceWithTaxes = new SimpleDoubleProperty();

	// Calculated item using bindings
	this.tvaAmount = Bindings.multiply(this.sellingPrice, this.tvaRate.divide(100));
}

Product.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		designation : this.designation.get(),
		sellingPrice : this.sellingPrice.get(),
		tvaRate : this.tvaRate.get()
	}
}

Product.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.designation.set(aJSONObject.get("designation"));
	this.sellingPrice.set(aJSONObject.get("sellingPrice"));
	this.tvaRate.set(aJSONObject.get("tvaRate"));
	this.href.set(aJSONObject.get("href"));

	this.sellingPriceWithTaxes.set(this.sellingPrice.multiply(this.tvaRate.divide(100).add(1)).get());
}

Product.prototype.parseProduct = function(aProduct) {
	this.uuid.set(null);
	this.designation.set(aProduct.designation.get());
	this.sellingPriceWithTaxes.set(aProduct.sellingPriceWithTaxes.get());
	this.tvaRate.set(aProduct.tvaRate.get());
}

Product.prototype.save = function() {
	$loader.getNamespace().put("$product", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/product",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Product.js");

			$product.parseJSON($result);

			alert("Save success");
		},
		error : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			alert("Save error");
		}
	});
}

Product.prototype.toString = function() {
	return this.designation.get();
}

Product.prototype.calcSellingPrice = function() {
	iSellingPrice = this.sellingPriceWithTaxes.divide(this.tvaRate.divide(100).add(1)).get()
	this.sellingPrice.set(iSellingPrice);
}