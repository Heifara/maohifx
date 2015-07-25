function Invoice() {
	this.invoiceNumber = 5;
	this.invoiceDate = new Date();
}

Invoice.prototype.save = function() {
	$http.ajax({
		url : "http://localhost:8080/maohifx.server/webapi/invoice",
		type : "post",
		contentType : "application/x-www-form-urlencoded",
		dataType : "application/json",
		data : {
			invoiceNumber : this.invoiceNumber,
			invoiceDate : this.invoiceDate
		},
		success : function($result, $status) {
			print($status);
			print($result);
			$tab.setText("Get sccess");
		},
	});
}