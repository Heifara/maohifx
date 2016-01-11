load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var Gender = com.maohi.software.maohifx.enumeration.Gender;

function FXMLTableViewTest() {
	this.data = FXCollections.observableArrayList();
	tableView.setItems(this.data);

	iPerson = new Person();
	iPerson.firstName.set("Jacob");
	iPerson.lastName.set("Smith")
	iPerson.age.set(16.0);
	iPerson.href.set("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
	iPerson.gender.set("MALE");
	this.data.add(iPerson);

	iPerson = new Person();
	iPerson.firstName.set("Johnson");
	iPerson.lastName.set("Isabell")
	iPerson.age.set(23.0);
	iPerson.href.set("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
	iPerson.gender.set("FEMALE");
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

FXMLTableViewTest.prototype.genderActionEvent = function(aCellActionEvent) {
	var iSelectedItem = aCellActionEvent.getItem();
	java.lang.System.out.println(iSelectedItem.toString());
}

FXMLTableViewTest.prototype.genderUpdateItem = function(aCellActionEvent) {
	iComboBoxTableCell = aCellActionEvent.getSource();
	java.lang.System.out.println(iComboBoxTableCell);

	iPerson = tableView.getItems().get(aCellActionEvent.getTableRow().getIndex());
	java.lang.System.out.println(iPerson);

	iComboBox = iComboBoxTableCell.getComboBox();
	iComboBox.getItems().addAll(Gender.values());
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