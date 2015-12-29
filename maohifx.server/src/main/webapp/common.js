/**
 * Basics loading
 */
load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

/**
 * Load all bean files
 */
load("http://localhost:8080/maohifx.server/bean/Contact.js");
load("http://localhost:8080/maohifx.server/bean/Customer.js");
load("http://localhost:8080/maohifx.server/bean/Email.js");
load("http://localhost:8080/maohifx.server/bean/Invoice.js");
load("http://localhost:8080/maohifx.server/bean/InvoiceLine.js");
load("http://localhost:8080/maohifx.server/bean/InvoicePaymentLine.js");
load("http://localhost:8080/maohifx.server/bean/PaymentMode.js");
load("http://localhost:8080/maohifx.server/bean/Phone.js");
load("http://localhost:8080/maohifx.server/bean/Product.js");
load("http://localhost:8080/maohifx.server/bean/Supplier.js");
load("http://localhost:8080/maohifx.server/bean/Tva.js");
load("http://localhost:8080/maohifx.server/bean/TvaReport.js");

/**
 * 
 */
var Application = javafx.application.Application;
var FXMLLoader = javafx.fxml.FXMLLoader;
var URL = java.net.URL;

var File = java.io.File;
var System = java.lang.System;
var Platform = javafx.application.Platform;

var Thread = java.lang.Thread;
var Runnable = java.lang.Runnable;
var StringBuilder = java.lang.StringBuilder;

var SimpleLocalDateProperty = com.maohi.software.maohifx.common.SimpleLocalDateProperty;
var ExtStringConverter = com.maohi.software.maohifx.common.ExtStringConverter;

var LocalDate = java.time.LocalDate;

/**
 * Global Method
 */

function alert(aText) {
	javax.swing.JOptionPane.showMessageDialog(null, aText);
}

function runLater(aRunnable) {
	Platform.runLater(aRunnable);
}

function disableControls(aParent) {
	iElements = this.getNodesOfType(aParent);
	for ( var iIndex in iElements) {
		iNode = iElements.get(iIndex);
		if (javafx.scene.control.Labeled.class.isAssignableFrom(iNode.getClass())) {
			iNode.setDisable(true);
		} else if (javafx.scene.control.MenuItem.class.isAssignableFrom(iNode.getClass())) {
			iNode.setDisable(true);
		} else if (javafx.scene.control.ComboBoxBase.class.isAssignableFrom(iNode.getClass())) {
			iNode.setEditable(false);
			iNode.setDisable(true);
		} else if (javafx.scene.control.Control.class.isAssignableFrom(iNode.getClass())) {
			iNode.setEditable(false);
		}
	}
}

function getNodesOfType(aParent) {
	var iElements = new java.util.ArrayList();

	// Different method to get children depends on Control
	var iChildren;
	if (aParent instanceof javafx.scene.control.ToolBar) {
		iChildren = aParent.getItems();
	} else {
		iChildren = aParent.getChildren();
	}

	for ( var iIndex in iChildren) {
		iNode = iChildren.get(iIndex);

		if (iNode instanceof javafx.scene.layout.Pane) {
			var iSubElements = this.getNodesOfType(iNode);
			for (iSubIndex in iSubElements) {
				var iSubNode = iSubElements.get(iSubIndex);
				iElements.add(iSubNode);
			}
		} else if (iNode instanceof javafx.scene.control.ToolBar) {
			var iSubElements = this.getNodesOfType(iNode);
			for (iSubIndex in iSubElements) {
				var iSubNode = iSubElements.get(iSubIndex);
				iElements.add(iSubNode);
			}
		} else {
			iElements.add(iNode);
		}
	}
	return iElements;
}