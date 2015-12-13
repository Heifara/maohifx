/**
 * 
 */
function Phone() {
	this.uuid = new SimpleStringProperty("");
	this.label = new SimpleStringProperty("");
	this.number = new SimpleStringProperty("");
}

Phone.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		label : this.label.get(),
		number : this.number.get,
	}
}

Phone.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.label.set(aJSONObject.get("label"));
	this.number.set(aJSONObject.get("number"));
}

Phone.prototype.save = function() {
	$loader.getNamespace().put("$phone", this);
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/contact",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			$contact.parseJSON($result);

			alert("Sauvegarde réussi");
		},
		error : function($result, $status) {
			print($status);

			alert("Erreur lors de la sauvegarde");
		}
	});
}
