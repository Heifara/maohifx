function ProductController() {
	this.packagings = FXCollections.observableArrayList();
	Packaging.search(this.packagings);

	this.product = new Product();
	if (typeof ($item) != "undefined") {
		this.product.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.product.designation);
		$tab.setIcon("@maohifx.server/product/tab.png")
	}

	tvaRate.setText(this.product.tva.toString());

	// Controls Binding
	designation.textProperty().bindBidirectional(this.product.designation);

	// Fill packagings with product_packagin list
	productPackagings.setConverter(new ExtStringConverter());
	this.fireProductPackagingChanged();

	// AutoCompletion
	Product.search(designationAutoCompletion);
	Tva.search(tvaAutoCompletion);

	runLater(new Runnable({
		run : function() {
			designation.requestFocus();
			productPackagings.getSelectionModel().select(0);
		}
	}));
}

ProductController.prototype.packagingSelectedEvent = function(aEvent) {
	iProductPackaging = productPackagings.getValue();
	if (iProductPackaging != null) {
		sellingPrice.setText(iProductPackaging.sellingPrice.get());
		sellingPriceWithTaxes.setText(iProductPackaging.sellingPrice.multiply(this.product.tva.rate.divide(100).add(1)).get());
	} else {
		sellingPrice.setText("");
		sellingPriceWithTaxes.setText("");
	}
}

ProductController.prototype.addPackagingEvent = function(aEvent) {
	iResponse = choice("Conditionnement", "Choisir un conditionnement", this.packagings);

	if (iResponse.isPresent()) {
		iPackaging = iResponse.get();
		this.product.addPackaging(iPackaging, false);
	}
	this.fireProductPackagingChanged();
}

ProductController.prototype.fireProductPackagingChanged = function(aEvent) {
	runLater(new Runnable({
		run : function() {
			productPackagings.getItems().clear();
			productPackagings.getItems().addAll(controller.product.productPackagings);
			productPackagings.getSelectionModel().select(0);
		}
	}));
}

ProductController.prototype.removePackagingEvent = function(aEvent) {
	this.product.removeProductPackaging(productPackagings.getValue());

	this.fireProductPackagingChanged();
}

ProductController.prototype.tvaAutoCompletionEvent = function(aEvent) {
	this.product.parseTva(aEvent.getCompletion());
}

ProductController.prototype.designationAutoCompletionEvent = function(aEvent) {
	this.product.parseProduct(aEvent.getCompletion());
}

ProductController.prototype.saveEvent = function(aEvent) {
	this.product.save();
}

ProductController.prototype.changeImageEvent = function(aEvent) {
	iFileChooser = new FileChooser();
	iFileChooser.setTitle("Open Resource File");
	iSelectedFile = iFileChooser.showOpenDialog($stage);
	iFile = new File(iSelectedFile);
	if (iFile.exists()) {
		iImage = new Image(iFile.toURI().toURL());
		picture.setImage(iImage);
		picture.setFitWidth(150);
		picture.setPreserveRatio(true);
		picture.setSmooth(true);
		picture.setCache(true);
		picture.setOpacity(10);
	}
}