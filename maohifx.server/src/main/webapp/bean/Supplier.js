Supplier.search = function(aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}

	iSearchResult = FXCollections.observableArrayList();
	$http.ajax({
		url : "@maohifx.server/webapi/supplier/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			for ( var iItem in $result) {
				iElement = new Supplier();
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
	$http.ajax({
		source : this,
		url : "@maohifx.server/webapi/supplier",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			this.source.parseJSON($result);

			alert("Save success");
		},
		error : function($result, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
}

Supplier.prototype.toString = function() {
	return this.code.get() + " " + this.lastname.get() + " " + this.firstname.get();
}
