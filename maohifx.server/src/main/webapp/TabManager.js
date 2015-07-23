load("http://localhost:8080/maohifx.server/common.js");

function TabManager() {

	this.newTab = function(aUrl) {
		try {
			var iNewTab = new Tab();

			iLoader = new FXMLLoader(new URL("http://localhost:8080/maohifx.server/webapi/fxml?id=newTab"));
			iLoader.getNamespace().put("mainPane", mainPane);
			iLoader.getNamespace().put("tabpane", tabpane);
			iLoader.getNamespace().put("defaultUrl", aUrl);
			iLoader.getNamespace().put("tab", iNewTab);

			iNewTab.setText("Nouvelle Onglet");
			iNewTab.setContent(iLoader.load());

			tabpane.getTabs().add(iNewTab);
			tabpane.getSelectionModel().select(tabpane.getTabs().indexOf(iNewTab));
		} catch (e) {
			print(e);
			print(e.getMessage());
		}
	};

	this.refresh = function() {
		if (!url.getText().isEmpty()) {
			try {
				iLoader = new FXMLLoader(new URL(url.getText()));
				iLoader.getNamespace().put("mainPane", mainPane);
				iLoader.getNamespace().put("tabpane", tabpane);
				iLoader.getNamespace().put("tab", tab);
				iLoader.getNamespace().put("toolbar", toolbar);
				iLoader.getNamespace().put("menuButton", menuButton);

				content.setCenter(iLoader.load());
			} catch (e) {
				print(e.getCause());
				print(e.getMessage());
			}
		}
	};
};
