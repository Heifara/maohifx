Product.search = function(aCollection, aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	$http.ajax({
		url : "@maohifx.server/webapi/product/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new Product();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
}

Product.readQuantities = function(aProduct) {
	$http.ajax({
		url : "@maohifx.server/webapi/product/quantities?uuid=" + aProduct.uuid.get(),
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aProduct.availableQuantity.set($result.get("availableQuantity"));
			aProduct.currentQuantity.set($result.get("currentQuantity"));
		},
		error : function($error, $status, $stackTrace) {
			error("Erreur durant la recherche", $error, "");
			System.err.println($stackTrace);
		}
	});

}

function Product() {
	this.uuid = new SimpleStringProperty();
	this.designation = new SimpleStringProperty();
	this.sellingPrice = new SimpleDoubleProperty();
	this.tva = new Tva();
	this.href = new SimpleStringProperty();

	this.availableQuantity = new SimpleDoubleProperty();
	this.currentQuantity = new SimpleDoubleProperty();

	this.productPackagings = FXCollections.observableArrayList();
	iPackaging = new Packaging();
	iPackaging.code.set("UNT");
	this.addPackaging(iPackaging, true);

	// Calculated item using bindings
	this.tvaAmount = Bindings.multiply(this.sellingPrice, this.tva.rate.divide(100));
}

Product.prototype.getMainProductPackaging = function() {
	for (iIndex in this.productPackagings) {
		iProductPackaging = this.productPackagings.get(iIndex);
		if (iProductPackaging.main.get()) {
			return iProductPackaging;
		}
	}

	return null;
}

Product.prototype.addPackaging = function(aPackaging, aMain) {
	iProductPackaging = new ProductPackaging();
	iProductPackaging.productUuid.set(this.uuid.get());
	iProductPackaging.packagingCode.set(aPackaging.code.get());
	iProductPackaging.packaging = aPackaging;
	iProductPackaging.main.set(aMain);
	iProductPackaging.sellingPrice.set(0.0);
	this.productPackagings.add(iProductPackaging);
}

Product.prototype.removeProductPackaging = function(aProductPackaging) {
	this.productPackagings.remove(aProductPackaging);
}

Product.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		designation : this.designation.get(),
		tva : this.tva.toJSON(),
		productPackagings : this.getProductPackagings(),
	}
}

Product.prototype.getProductPackagings = function() {
	iArrayList = new java.util.ArrayList();
	for ( var index in this.productPackagings) {
		iArrayList.add(this.productPackagings.get(index).toJSON());
	}

	return iArrayList;
}

Product.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.designation.set(aJSONObject.get("designation"));
	this.sellingPrice.set(aJSONObject.get("sellingPrice"));
	this.tva.parseJSON(aJSONObject.get("tva"));
	this.href.set(aJSONObject.get("href"));

	this.productPackagings.clear();
	iArray = aJSONObject.get("productPackagings");
	for ( var iIndex in iArray) {
		iProductPackaging = new ProductPackaging();
		iProductPackaging.parseJSON(iArray[iIndex]);
		this.productPackagings.add(iProductPackaging);
	}

	Product.readQuantities(this);
}

Product.prototype.parseTva = function(aTva) {
	this.tva = aTva;
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
		error : function($error, $stackTrace) {
			error("Erreur lors de la sauvegarde", $error, $stackTrace);
		}
	});
}

Product.prototype.toString = function() {
	return this.designation.get();
}
