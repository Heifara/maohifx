load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

var ClientBuilder = javax.ws.rs.client.ClientBuilder;
var Entity = javax.ws.rs.client.Entity;
var MediaType = javax.ws.rs.core.MediaType;

function homeEvent(aEvent) {
	url.setText("http://localhost:8080/maohifx-server/webapi/fxml?id=home");
	refresh();
}

function aboutEvent(aEvent) {
	newTab("http://localhost:8080/maohifx-server/webapi/fxml?id=about");
}

function post() {
	return ClientBuilder.newClient().target("http://localhost:8080/maohifx-server/webapi/post").request().post(Entity.entity(new String("Hello World"), MediaType.APPLICATION_JSON));
}