Supplier.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/supplier/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Supplier.js");
			load("http://localhost:8080/maohifx.server/bean/Contact.js");
			load("http://localhost:8080/maohifx.server/bean/Email.js");
			load("http://localhost:8080/maohifx.server/bean/Phone.js");

			for ( var iItem in $result) {
				iElement = new Supplier();
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

function Supplier() {
	this.uuid = new SimpleStringProperty();
	this.href = new SimpleStringProperty();
	this.code = new SimpleStringProperty();

	this.contact = new Contact();

	this.uuid.bindBidirectional(this.contact.uuid);

	this.lastname = new SimpleStringProperty();
	this.lastname.bindBidirectional(this.contact.lastname);

	this.firstname = new SimpleStringProperty();
	this.firstname.bindBidirectional(this.contact.firstname);
}

Supplier.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		code : this.code.get(),
		contact : this.contact.toJSON(),
	}
}

Supplier.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.href.set(aJSONObject.get("href"));
	this.code.set(aJSONObject.get("code"));
	this.contact.parseJSON(aJSONObject.get("contact"));
}

Supplier.prototype.save = function() {
	$loader.getNamespace().put("$element", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/supplier",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Supplier.js");

			$element.parseJSON($result);

			alert("Save success");
		},
		error : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			alert($status);
		}
	});
}

Supplier.prototype.toString = function() {
	return this.code.get() + " " + this.lastname.get() + " " + this.firstname.get();
}
