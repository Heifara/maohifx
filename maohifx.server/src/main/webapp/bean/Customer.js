Customer.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/customer/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Customer.js");
			load("http://localhost:8080/maohifx.server/bean/Contact.js");
			load("http://localhost:8080/maohifx.server/bean/Email.js");
			load("http://localhost:8080/maohifx.server/bean/Phone.js");

			for ( var iItem in $result) {
				iElement = new Customer();
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

function Customer() {
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

Customer.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		code : this.code.get(),
		contact : this.contact.toJSON(),
	}
}

Customer.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.href.set(aJSONObject.get("href"));
	this.code.set(aJSONObject.get("code"));
	this.contact.parseJSON(aJSONObject.get("contact"));
}

Customer.prototype.save = function() {
	$loader.getNamespace().put("$element", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/customer",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Customer.js");

			$element.parseJSON($result);

			alert("Save success");
		},
		error : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			alert($status);
		}
	});
}

Customer.prototype.toString = function() {
	return this.lastname.get() + " " + this.firstname.get();
}
