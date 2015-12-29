load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");
load("http://localhost:8080/maohifx.server/bean/Tva.js");

function ProductController() {
	this.product = new Product();
	if (typeof ($item) != "undefined") {
		this.product.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.product.designation);
	}

	tvaRate.setText(this.product.tva.toString());

	// Controls Binding
	designation.textProperty().bindBidirectional(this.product.designation);
	sellingPrice.textProperty().bindBidirectional(this.product.sellingPrice, new ExtStringConverter());
	sellingPriceWithTaxes.textProperty().bindBidirectional(this.product.sellingPriceWithTaxes, new ExtStringConverter());

	// Adding ChangeListener
	this.product.sellingPriceWithTaxes.addListener(new ChangeListener({
		changed : function(aObservable, aOldValue, aNewValue) {
			controller.product.calcSellingPrice();
		}
	}));

	// AutoCompletion
	designationAutoCompletion.addAll(Product.search());
	tvaAutoCompletion.addAll(Tva.search());

	runLater(new Runnable({
		run : function() {
			designation.requestFocus();
		}
	}));
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