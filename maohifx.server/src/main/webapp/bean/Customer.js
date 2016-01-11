Customer.search = function(aCollection, aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}
	$http.ajax({
		url : "@maohifx.server/webapi/customer/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new Customer();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
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
	$http.ajax({
		source : this,
		url : "@maohifx.server/webapi/customer",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			this.source.parseJSON($result);

			alert("Save success");
		},
		error : function($error, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
}

Customer.prototype.toString = function() {
	if (this.code.get() == null) {
		return "";
	}

	return this.code.get() + " " + this.lastname.get() + " " + this.firstname.get();
}
