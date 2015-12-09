load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

function FXMLTableViewTest() {
	this.data = FXCollections.observableArrayList();

	iPerson = new Person();
	iPerson.firstName.set("Jacob");
	iPerson.lastName.set("Smith")
	iPerson.age.set(16.0);
	iPerson.href.set("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
	this.data.add(iPerson);

	iPerson = new Person();
	iPerson.firstName.set("Isabella");
	iPerson.lastName.set("Johnson")
	iPerson.age.set(15.0);
	iPerson.href.set("http://localhost:8080/maohifx.server/webapi/fxml?id=index");
	this.data.add(iPerson);

	tableView.setItems(this.data);

	filter.textProperty().bindBidirectional(tableView.filterProperty());
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