load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

function FXMLTableViewTest() {
	this.data = FXCollections.observableArrayList();
	tableView.setItems(this.data);

	iPerson = new Person();
	iPerson.firstName.set("Jacob");
	iPerson.lastName.set("Smith")
	iPerson.age.set(16.0);
	iPerson.href.set("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
	this.data.add(iPerson);

	filter.textProperty().bindBidirectional(tableView.filterProperty());

	this.addAutoCompletion("Smith", "Jacob");
	this.addAutoCompletion("Johnson", "Isabella");
	this.addAutoCompletion("Jacobson", "Igor");
	this.addAutoCompletion("Syrano", "Bergerac");
}

FXMLTableViewTest.prototype.addAutoCompletion = function(aLastName, aFirstName) {
	iPerson = new Person();
	iPerson.firstName.set(aFirstName);
	iPerson.lastName.set(aLastName);
	autoCompletion.add(iPerson);
}

FXMLTableViewTest.prototype.actionEvent = function() {
	javax.swing.JOptionPane.showMessageDialog(null, "Hello world");
}

FXMLTableViewTest.prototype.selectRowOneEvent = function() {
	tableView.select(1);
}

FXMLTableViewTest.prototype.onEditCommitAgeEvent = function() {
	java.lang.System.out.println("onEditCommitAgeEvent");
}

FXMLTableViewTest.prototype.onEditCommitLastnameEvent = function() {
	java.lang.System.out.println("onEditCommitLastnameEvent");
}

FXMLTableViewTest.prototype.autoCompletionEvent = function(aEvent) {
	iPerson = aEvent.getCompletion();
	java.lang.System.out.println(iPerson.toString());
}