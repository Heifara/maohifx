function ProductsController() {
	if (typeof ($tab) != 'undefined') {
		$tab.setText("Produits");
	}

	Platform.runLater(new Runnable({
		run : function() {
			pattern.requestFocus();
		}
	}));

	Product.search(patternAutoCompletion)
}

ProductsController.prototype.searchEvent = function(aEvent) {
	Product.search(tableView.getItems(), pattern.getText());
}
