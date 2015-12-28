Salesman.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$loader.getNamespace().put("$data", iSearchResult);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/salesman/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Customer.js");
			load("http://localhost:8080/maohifx.server/bean/Contact.js");
			load("http://localhost:8080/maohifx.server/bean/Email.js");
			load("http://localhost:8080/maohifx.server/bean/Phone.js");
			load("http://localhost:8080/maohifx.server/bean/Salesman.js");

			for ( var iItem in $result) {
				iElement = new Salesman();
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

function Salesman() {
	this.uuid = new SimpleStringProperty();
	this.href = new SimpleStringProperty();
	this.code = new SimpleStringProperty();
	this.salesCommission = new SimpleDoubleProperty();

	this.contact = new Contact();

	this.uuid.bindBidirectional(this.contact.uuid);

	this.lastname = new SimpleStringProperty();
	this.lastname.bindBidirectional(this.contact.lastname);

	this.firstname = new SimpleStringProperty();
	this.firstname.bindBidirectional(this.contact.firstname);
}

Salesman.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		code : this.code.get(),
		salesCommission : this.salesCommission.get(),
		contact : this.contact.toJSON(),
	}
}

Salesman.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.href.set(aJSONObject.get("href"));
	this.code.set(aJSONObject.get("code"));
	this.salesCommission.set(aJSONObject.get("salesCommission"));
	this.contact.parseJSON(aJSONObject.get("contact"));
}

Salesman.prototype.save = function() {
	$loader.getNamespace().put("$element", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/salesman",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Customer.js");
			load("http://localhost:8080/maohifx.server/bean/Contact.js");
			load("http://localhost:8080/maohifx.server/bean/Email.js");
			load("http://localhost:8080/maohifx.server/bean/Phone.js");
			load("http://localhost:8080/maohifx.server/bean/Salesman.js");

			$element.parseJSON($result);

			alert("Save success");
		},
		error : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			alert($status);
		}
	});
}

Salesman.prototype.toString = function() {
	return this.code.get() + " " + this.lastname.get() + " " + this.firstname.get();
}
