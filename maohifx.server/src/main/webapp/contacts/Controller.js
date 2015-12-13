load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

function ContactsController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Contact");
	}

	this.data = FXCollections.observableArrayList();
	tableView.setItems(this.data);
}

ContactsController.prototype.searchEvent = function(aEvent) {
	$loader.getNamespace().put("$data", this.data);
	this.data.clear();
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/contacts",
		type : "get",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		success : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");
			load("http://localhost:8080/maohifx.server/bean/Contact.js");
			load("http://localhost:8080/maohifx.server/bean/Email.js");
			load("http://localhost:8080/maohifx.server/bean/Phone.js");

			for ( var item in $result) {
				iContact = new Contact();
				iContact.parseJSON($result[item]);
				$data.add(iContact);
			}
		},
		error : function($result, $status) {
			load("http://localhost:8080/maohifx.server/common.js");

			System.err.println($result);
			System.err.println($status);
		}
	});
}
