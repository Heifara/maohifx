function ProductController() {
	this.product = new Product();
	if (typeof ($item) != "undefined") {
		this.product.parseJSON($item);
		this.fireProductPackagingChanged();
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.product.designation);
		$tab.setIcon("@maohifx.server/product/tab.png")
	}

	tvaRate.setText(this.product.tva.toString());

	// Controls Binding
	designation.textProperty().bindBidirectional(this.product.designation);

	// Fill packagings with product_packagin list
	packagings.setConverter(new ExtStringConverter());

	// AutoCompletion
	Product.search(designationAutoCompletion);
	Tva.search(tvaAutoCompletion);

	runLater(new Runnable({
		run : function() {
			designation.requestFocus();
			packagings.getSelectionModel().select(0);
		}
	}));
}

ProductController.prototype.packagingSelectedEvent = function(aEvent) {
	iProductPackaging = packagings.getValue();
	if (iProductPackaging != null) {
		sellingPrice.setText(iProductPackaging.sellingPrice.get());
		sellingPriceWithTaxes.setText(iProductPackaging.sellingPrice.multiply(this.product.tva.rate.divide(100).add(1)).get());
	} else {
		sellingPrice.setText("");
		sellingPriceWithTaxes.setText("");
	}
}

ProductController.prototype.addPackagingEvent = function(aEvent) {
	iChoices = new java.util.ArrayList();
	Packaging.search(iChoices);
	Thread.sleep(1000);// Because ajax is executed in a thread
	iResponse = choice("Conditionnement", "Choisir un conditionnement", iChoices);

	if (iResponse.isPresent()) {
		iPackaging = iResponse.get();
		this.product.addPackaging(iPackaging, false);
	}
	this.fireProductPackagingChanged();
}

ProductController.prototype.fireProductPackagingChanged = function(aEvent) {
	runLater(new Runnable({
		run : function() {
			packagings.getItems().clear();
			packagings.getItems().addAll(controller.product.productPackagings);
			packagings.getSelectionModel().select(0);
		}
	}));
}

ProductController.prototype.removePackagingEvent = function(aEvent) {
	this.product.removeProductPackaging(packagings.getValue());

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