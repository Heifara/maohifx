/**
 * 
 */
function Product() {
	this.uuid = new SimpleStringProperty();
	this.designation = new SimpleStringProperty();
	this.sellingPrice = new SimpleDoubleProperty();
	this.href = new SimpleStringProperty();
}

Product.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		designation : this.designation.get(),
		sellingPrice : this.sellingPrice.get()
	}
}

Product.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.designation.set(aJSONObject.get("uuid"));
	this.sellingPrice.set(aJSONObject.get("number"));
	this.href.set("http://localhost:8080/maohifx.server/webapi/product?uuid=" + this.uuid.get() + "");
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
			load("http://localhost:8080/maohifx.server/bean/Invoice.js");
			load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");
			$invoice.parseJSON($result);
		},
		error : function($result, $status) {
			print($status);
		}
	});
}
