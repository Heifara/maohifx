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

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.contact.lastname);
	}

	lastname.textProperty().bindBidirectional(this.contact.lastname);
	middlename.textProperty().bindBidirectional(this.contact.middlename);
	firstname.textProperty().bindBidirectional(this.contact.firstname);

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

ContactController.prototype.addPhoneEvent = function(aActionEvent) {
	if (this.lastAddedPhone != null && !this.lastAddedPhone.number.get().isEmpty()) {
		this.lastAddedPhone = this.contact.addPhone();

		this.phoneRowIndex++;

		iLabelTextField = new TextField();
		iLabelTextField.getStyleClass().add("label-text-field");
		iLabelTextField.setText("");
		iLabelTextField.setFocusTraversable(false);
		iLabelTextField.textProperty().bindBidirectional(this.lastAddedPhone.label);
		phone.add(iLabelTextField, 0, this.phoneRowIndex);

		iTextField = new TextField();
		iTextField.textProperty().bindBidirectional(this.lastAddedPhone.number);
		iTextField.onAction = new javafx.event.EventHandler({
			handle : function(aActionEvent) {
				controller.addPhoneEvent(aActionEvent);
			}
		});
		phone.add(iTextField, 1, this.phoneRowIndex);

		iTextField.requestFocus();
	}
}

ContactController.prototype.addEmailEvent = function(aActionEvent) {
	if (this.lastAddedEmail != null && !this.lastAddedEmail.mail.get().isEmpty()) {
		iEmail = this.contact.addEmail();
		this.lastAddedEmail = iEmail;

		this.emailRowIndex++;

		iLabelTextField = new TextField();
		iLabelTextField.getStyleClass().add("label-text-field");
		iLabelTextField.setText("");
		iLabelTextField.setFocusTraversable(false);
		iLabelTextField.textProperty().bindBidirectional(iEmail.label);
		email.add(iLabelTextField, 0, this.emailRowIndex);

		iTextField = new TextField();
		iTextField.textProperty().bindBidirectional(iEmail.mail);
		iTextField.onAction = new javafx.event.EventHandler({
			handle : function(aActionEvent) {
				controller.addEmailEvent();
			}
		});
		email.add(iTextField, 1, this.emailRowIndex);

		iTextField.requestFocus();
	}
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