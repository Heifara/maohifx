load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");

function ProductController() {
	this.product = new Product();
	if (typeof ($item) != "undefined") {
		this.product.parseJSON($item);
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.product.designation);
	}

	// Controls Binding
	designation.textProperty().bindBidirectional(this.product.designation);
	sellingPrice.textProperty().bindBidirectional(this.product.sellingPrice, new JSObjectStringConverter());
	tvaRate.textProperty().bindBidirectional(this.product.tvaRate, new JSObjectStringConverter());
	sellingPriceWithTaxes.textProperty().bindBidirectional(this.product.sellingPriceWithTaxes, new JSObjectStringConverter());

	// Adding ChangeListener
	this.product.sellingPriceWithTaxes.addListener(new ChangeListener({
		changed : function(aObservable, aOldValue, aNewValue) {
			controller.product.calcSellingPrice();
		}
	}));
	this.product.tvaRate.addListener(new ChangeListener({
		changed : function(aObservable, aOldValue, aNewValue) {
			controller.product.calcSellingPrice();
		}
	}));

	// AutoCompletion
	designationAutoCompletion.addAll(Product.search());
	
	runLater(new Runnable({
		run : function() {
			designation.requestFocus();
		}
	}));
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