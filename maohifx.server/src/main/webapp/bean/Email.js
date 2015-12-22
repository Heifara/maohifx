/**
 * 
 */
function Email() {
	this.uuid = new SimpleStringProperty("");
	this.label = new SimpleStringProperty("");
	this.mail = new SimpleStringProperty("");
}

Email.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		label : this.label.get(),
		email : this.mail.get(),
	}
}

Email.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.label.set(aJSONObject.get("label"));
	this.mail.set(aJSONObject.get("email"));
}

Email.prototype.save = function() {
	$loader.getNamespace().put("$mail", this);
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
