load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");

function ProductsController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Produits");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));
}

ProductsController.prototype.searchEvent = function(aEvent) {
	tableView.setItems(Product.search(pattern.getText()));
}
