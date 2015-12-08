/**
 *
 */
package com.maohi.software.maohifx.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

/**
 * @author heifara
 *
 */
public interface LinkTarget {

	void error(Link link, ActionEvent aEvent, Throwable aException);

	void handle(Link link, ActionEvent aEvent, FXMLLoader aLoader);

}
