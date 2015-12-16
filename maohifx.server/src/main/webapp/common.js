load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

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