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
		number : this.number.get(),
	}
}

Phone.prototype.parseJSON = function(aJSONObject) {
	this.uuid.set(aJSONObject.get("uuid"));
	this.label.set(aJSONObject.get("label"));
	this.number.set(aJSONObject.get("number"));
}