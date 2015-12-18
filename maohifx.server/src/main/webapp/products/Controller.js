load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");
load("http://localhost:8080/maohifx.server/bean/Tva.js");

function ProductsController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Produits");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));

	// AutoCompletion
	iResults = Product.search();
	for (iIndex in iResults) {
		iProduct = iResults.get(iIndex);
		patternAutoCompletion.add(iProduct.designation.get());
	}
}

ProductsController.prototype.searchEvent = function(aEvent) {
	tableView.setItems(Product.search(pattern.getText()));
}
