load("http://localhost:8080/maohifx.server/common.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");

function ProductController() {
	this.product = new Product();
	if (typeof ($product) != 'undefined') {
		this.product = $product;
	}

	if (typeof ($tab) != 'undefined') {
		$tab.textProperty().bindBidirectional(this.product.designation);
	}

	designation.textProperty().bindBidirectional(this.product.designation);

}

ProductController.prototype.saveEvent = function(aEvent) {
	this.product.save();
}

ProductController.prototype.handleStartEvent = function(aEvent) {
	iNamespace = aEvent.getNamespace();
	iNamespace.put("$product", this.product);
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