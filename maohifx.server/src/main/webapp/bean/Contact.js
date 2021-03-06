Contact.search = function(aCollection, aPattern) {
	iPattern = "";
	if (typeof (aPattern) != 'undefined') {
		iPattern = aPattern.replaceAll(" ", "+");
	}
	$http.ajax({
		url : "@maohifx.server/webapi/contact/search?pattern=" + iPattern,
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			aCollection.clear();
			for ( var iItem in $result) {
				iElement = new Contact();
				iElement.parseJSON($result[iItem]);
				aCollection.add(iElement);
			}
		},
		error : function($error, $status, $stackTrace) {
			error("Erreur durant la recherche", $error, $stackTrace);
		}
	});
}

function Contact() {
	this.uuid = new SimpleStringProperty();
	this.href = new SimpleStringProperty();
	this.society = new SimpleBooleanProperty();
	this.lastname = new SimpleStringProperty();
	this.middlename = new SimpleStringProperty();
	this.firstname = new SimpleStringProperty();
	this.job = new SimpleStringProperty();
	this.birthdate = new SimpleLocalDateProperty();

	this.phones = FXCollections.observableArrayList();
	this.emails = FXCollections.observableArrayList();
}

Contact.prototype.toJSON = function() {
	return {
		uuid : this.uuid.get(),
		// society : this.society.get(),
		lastname : this.lastname.get(),
		middlename : this.middlename.get(),
		firstname : this.firstname.get(),
		phones : this.getPhones(),
		emails : this.getEmails()
	}
}

Contact.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.society.set(aJSONObject.get("society"));
	this.lastname.set(aJSONObject.get("lastname"));
	this.middlename.set(aJSONObject.get("middlename"));
	this.firstname.set(aJSONObject.get("firstname"));
	this.href.set(aJSONObject.get("href"));

	this.emails.clear();
	iArray = aJSONObject.get("emails");
	for ( var iIndex in iArray) {
		iItem = new Email();
		iItem.parseJSON(iArray[iIndex]);
		this.emails.add(iItem);
	}

	this.phones.clear();
	iArray = aJSONObject.get("phones");
	for ( var iIndex in iArray) {
		iItem = new Phone();
		iItem.parseJSON(iArray[iIndex]);
		this.phones.add(iItem);
	}
}

Contact.prototype.toString = function() {
	return this.lastname.get() + " " + this.firstname.get();
}

Contact.prototype.save = function() {
	$http.ajax({
		source : this,
		url : "@maohifx.server/webapi/contact",
		type : "put",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : this.toJSON(),
		success : function($result, $status) {
			this.source.parseJSON($result);

			alert("Sauvegarde réussi");
		},
		error : function($error, $stackTrace) {
			error("Erreur lors de la sauvegarde", $error, $stackTrace);
		}

	});
}

Contact.prototype.addEmail = function() {
	iEmail = new Email();
	iEmail.label.set("E-Mail");
	iEmail.mail.set("");

	this.emails.add(iEmail);

	return iEmail;
}

Contact.prototype.addPhone = function() {
	iPhone = new Phone();
	iPhone.label.set("Mobile");
	iPhone.number.set("");

	this.phones.add(iPhone);

	return iPhone;
}

Contact.prototype.getLastEmail = function() {
	if (this.emails.size() == 0) {
		return null;
	}

	return this.emails.get(this.emails.size() - 1);
}

Contact.prototype.getLastPhone = function() {
	if (this.phones.size() == 0) {
		return null;
	}

	return this.phones.get(this.phones.size() - 1);
}

Contact.prototype.getPhones = function() {
	iArrayList = new java.util.ArrayList();
	for ( var index in this.phones) {
		iArrayList.add(this.phones.get(index).toJSON());
	}

	return iArrayList;
}

Contact.prototype.getEmails = function() {
	iArrayList = new java.util.ArrayList();
	for ( var index in this.emails) {
		iArrayList.add(this.emails.get(index).toJSON());
	}

	return iArrayList;
}

Contact.prototype.removeLastEmail = function() {
	if (this.emails.size() > 0) {
		this.emails.remove(this.emails.size() - 1);
	}
}

Contact.prototype.removeLastPhone = function() {
	if (this.phones.size() > 0) {
		this.phones.remove(this.phones.size() - 1);
	}
}
