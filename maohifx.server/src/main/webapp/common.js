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
var JSObjectStringConverter = com.maohi.software.maohifx.control.cell.JSObjectStringConverter;

function alert(aText) {
	javax.swing.JOptionPane.showMessageDialog(null, aText);
}

function runLater(aRunnable) {
	Platform.runLater(aRunnable);
}