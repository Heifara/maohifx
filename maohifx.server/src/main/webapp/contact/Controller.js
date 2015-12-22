load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");

function ContactController() {
	this.emailRowIndex = 0;
	this.phoneRowIndex = 0;

	this.lastAddedPhone = new Phone();
	this.lastAddedPhone.number.set("000000");

	this.lastAddedEmail = new Email();
	this.lastAddedEmail.mail.set("lsdjflsdjmlj");

	this.contact = new Contact();
	if (typeof ($item) != 'undefined') {
		this.contact.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.contact.lastname);
	}

	lastname.textProperty().bindBidirectional(this.contact.lastname);
	middlename.textProperty().bindBidirectional(this.contact.middlename);
	firstname.textProperty().bindBidirectional(this.contact.firstname);

	// Populating UI with phones from contact
	for (iIndex in this.contact.phones) {
		this.addPhoneEvent(this.contact.phones.get(iIndex));
	}
	// Populating UI with emails from contact
	for (iIndex in this.contact.emails) {
		this.addEmailEvent(this.contact.emails.get(iIndex));
	}

	this.addEmailEvent();
	this.addPhoneEvent();
}

ContactController.prototype.saveEvent = function(aActionEvent) {
	iEmail = this.contact.getLastEmail();
	if (iEmail != null && iEmail.mail.get().isEmpty()) {
		this.contact.removeLastEmail();
	}

	iPhone = this.contact.getLastPhone();
	if (iPhone != null && iPhone.number.get().isEmpty()) {
		this.contact.removeLastPhone();
	}

	this.contact.save();
}

ContactController.prototype.addPhoneEvent = function(aPhone) {
	if (typeof (aPhone) != 'undefined') {
		this.lastAddedPhone = aPhone;
	} else {
		this.lastAddedPhone = this.contact.addPhone();
	}

	this.phoneRowIndex++;

	iLabelTextField = new TextField();
	iLabelTextField.getStyleClass().add("label-text-field");
	iLabelTextField.setText(this.lastAddedPhone.label);
	iLabelTextField.setFocusTraversable(false);
	iLabelTextField.textProperty().bindBidirectional(this.lastAddedPhone.label);
	phone.add(iLabelTextField, 0, this.phoneRowIndex);

	iTextField = new TextField();
	iTextField.setText(this.lastAddedPhone.number);
	iTextField.textProperty().bindBidirectional(this.lastAddedPhone.number);
	iTextField.onAction = new javafx.event.EventHandler({
		handle : function() {
			if (controller.lastAddedPhone != null && !controller.lastAddedPhone.number.get().isEmpty()) {
				controller.addPhoneEvent();
			}
		}
	});
	phone.add(iTextField, 1, this.phoneRowIndex);

	iTextField.requestFocus();
}

ContactController.prototype.addEmailEvent = function(aEmail) {
	if (typeof (aEmail) != 'undefined') {
		this.lastAddedEmail = aEmail;
	} else {
		this.lastAddedEmail = this.contact.addEmail();
	}

	this.emailRowIndex++;

	iLabelTextField = new TextField();
	iLabelTextField.getStyleClass().add("label-text-field");
	iLabelTextField.setText("");
	iLabelTextField.setFocusTraversable(false);
	iLabelTextField.textProperty().bindBidirectional(this.lastAddedEmail.label);
	email.add(iLabelTextField, 0, this.emailRowIndex);

	iTextField = new TextField();
	iTextField.textProperty().bindBidirectional(this.lastAddedEmail.mail);
	iTextField.onAction = new javafx.event.EventHandler({
		handle : function(aActionEvent) {
			if (controller.lastAddedEmail != null && !controller.lastAddedEmail.mail.get().isEmpty()) {
				controller.addEmailEvent();
			}
		}
	});
	email.add(iTextField, 1, this.emailRowIndex);

	iTextField.requestFocus();
}

ContactController.prototype.changeImageEvent = function(aEvent) {
	iFileChooser = new FileChooser();
	iFileChooser.setTitle("Open Resource File");
	iSelectedFile = iFileChooser.showOpenDialog($stage);
	if (iSelectedFile != null) {
		iImage = new Image(iSelectedFile.toURI().toURL());
		picture.setImage(iImage);
		picture.setFitWidth(150);
		picture.setPreserveRatio(true);
		picture.setSmooth(true);
		picture.setCache(true);
		picture.setOpacity(10);
	}
}