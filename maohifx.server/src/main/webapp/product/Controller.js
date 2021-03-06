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
	availableQuantity.textProperty().bindBidirectional(this.product.availableQuantity, new ExtStringConverter());
	currentQuantity.textProperty().bindBidirectional(this.product.currentQuantity, new ExtStringConverter());

	// Fill packagings with product_packagin list
	productPackagings.setConverter(new ExtStringConverter());
	this.fireProductPackagingChanged();

	productPackagingLot.setConverter(new ExtStringConverter());
	productPackagingLot.getItems().addListener(new ListChangeListener({
		onChanged : function(aChange) {
			runLater(new Runnable({
				run : function() {
					productPackagingLot.getSelectionModel().select(0);
				}
			}));
		}
	}));

	// AutoCompletion
	Product.search(designationAutoCompletion);
	Tva.search(tvaAutoCompletion);
}

ProductController.prototype.mainChangeEvent = function(aEvent) {
	if (yesno("Changement d'unité principal", "Attention!! le changement d'unité principal peut avoir des conséquences sur les stock", "Voulez vous continuer ?")) {
		iProductPackaging = productPackagings.getValue();
		iProductPackaging.main.set(mainProductPackaging.isSelected());
		console.log(iProductPackaging.main.get());
	}

	mainProductPackaging.setSelected(iProductPackaging.main.get());
}

ProductController.prototype.sellingPriceWithTaxesEvent = function(aEvent) {
	this.fireSellingPriceValue();
}

ProductController.prototype.packagingSelectedEvent = function(aEvent) {
	iProductPackaging = productPackagings.getValue();
	if (iProductPackaging != null) {
		mainProductPackaging.setSelected(iProductPackaging.main.get());
		sellingPrice.setText(iProductPackaging.sellingPrice.get());
		sellingPriceWithTaxes.setText(iProductPackaging.sellingPrice.multiply(this.product.tva.rate.divide(100).add(1)).get());

		var iPackagingCode = iProductPackaging.packagingCode.get();
		var iProductUuid = iProductPackaging.productUuid.get();
		productPackagingLot.getItems().clear();
		ProductPackagingLot.search(productPackagingLot.getItems(), iProductPackaging);
	} else {
		sellingPrice.setText("");
		sellingPriceWithTaxes.setText("");
	}
}

ProductController.prototype.productPackagingLotSelectedEvent = function(aEvent) {
	var iProductPackagingLot = productPackagingLot.getValue();
	console.log(iProductPackagingLot);

	if (iProductPackagingLot != null) {
		costPrice.setText(iProductPackagingLot.costPrice.get());
		weightedAverageCostPrice.setText(iProductPackagingLot.weightedAverageCostPrice.get());
		// bestBefore.setValue(iProductPackagingLot.bestBefore.getDate());
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

ProductController.prototype.fireSellingPriceValue = function(aEvent) {
	iSellingPriceWithTaxes = new SimpleDoubleProperty(sellingPriceWithTaxes.getText());
	iProductPackaging = productPackagings.getValue();
	iProductPackaging.sellingPrice.set(iSellingPriceWithTaxes.get() / ((this.product.tva.rate.get() / 100) + 1));
	sellingPrice.setText(iProductPackaging.sellingPrice.get());
}

ProductController.prototype.fireProductPackagingChanged = function(aEvent) {
	runLater(new Runnable({
		run : function() {
			productPackagings.getItems().clear();
			productPackagings.getItems().addAll(controller.product.productPackagings);

			var iMainProductPackaging = controller.product.getMainProductPackaging();
			if (iMainProductPackaging != null) {
				productPackagings.getSelectionModel().select(iMainProductPackaging);
			}

			controller.packagingSelectedEvent();
		}
	}));
}

ProductController.prototype.removePackagingEvent = function(aEvent) {
	this.product.removeProductPackaging(productPackagings.getValue());

	this.fireProductPackagingChanged();
}

ProductController.prototype.tvaAutoCompletionEvent = function(aEvent) {
	this.product.parseTva(aEvent.getCompletion());

	this.fireSellingPriceValue();
}

ProductController.prototype.designationAutoCompletionEvent = function(aEvent) {
	this.product.parseProduct(aEvent.getCompletion());
}

ProductController.prototype.saveEvent = function(aEvent) {
	var iMainProductPackaging = this.product.getMainProductPackaging();
	if (iMainProductPackaging == null) {
		error("Unité principal manquant", "Une unité principal est obligatoire", "");
		return;
	}

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