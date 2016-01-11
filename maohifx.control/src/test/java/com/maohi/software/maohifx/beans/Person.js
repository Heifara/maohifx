function Person() {
	this.firstName = new SimpleStringProperty();
	this.lastName = new SimpleStringProperty("");
	this.href = new SimpleStringProperty();
	this.age = new SimpleDoubleProperty();
	this.gender = new SimpleStringProperty("");
}

Person.prototype.toString = function() {
	return this.lastName.get();
}
