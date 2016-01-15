/**
 * Basics loading
 */
load("fx:base.js");
load("fx:controls.js");
load("fx:graphics.js");

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
	runLater(new Runnable({
		run : function() {
			org.controlsfx.dialog.Dialogs.create().title("").message(aText).showInformation();
		}
	}));
}

function info(aTitle, aMessage) {
	runLater(new Runnable({
		run : function() {
			org.controlsfx.dialog.Dialogs.create().title(aTitle).message(aMessage).showWarning();
		}
	}));
}

function warn(aTitle, aMessage) {
	runLater(new Runnable({
		run : function() {
			org.controlsfx.dialog.Dialogs.create().title(aTitle).message(aMessage).showWarning();
		}
	}));
}

function error(aTitle, aErreur, aStackTrace) {
	runLater(new Runnable({
		run : function() {
			org.controlsfx.dialog.Dialogs.create().title(aTitle).masthead(aErreur).message(aStackTrace).showError();
		}
	}));
}

/**
 * 
 * @param aTitle
 * @param aMessage
 * @param aChoices
 * @returns Optional<String> response
 */
function choice(aTitle, aMessage, aChoices) {
	return org.controlsfx.dialog.Dialogs.create().title(aTitle).message(aMessage).showChoices(aChoices);
}

function yesno(aTitle, aMastHead, aMessage) {
	iResponse = org.controlsfx.dialog.Dialogs.create().title(aTitle).masthead(aMastHead).message(aMessage).showConfirm();
	console.log(iResponse);
	if (iResponse == org.controlsfx.dialog.Dialog.ACTION_YES) {
		console.log("true");
		return true;
	} else {
		console.log("false");
		return false;
	}
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