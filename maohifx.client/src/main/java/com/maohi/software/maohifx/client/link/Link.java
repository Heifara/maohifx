/**
 * 
 */
package com.maohi.software.maohifx.client.link;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.maohi.software.maohifx.client.ExtFXMLLoader;
import com.maohi.software.maohifx.client.extendedtab.ExtendedTab;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TabPane;

/**
 * @author heifara
 *
 */
public class Link extends Hyperlink implements Initializable, EventHandler<ActionEvent> {

	private String href;

	public Link() {
		try {
			final ExtFXMLLoader iLoader = new ExtFXMLLoader();
			iLoader.setLocation(this.getClass().getResource("Link.fxml"));
			iLoader.setRoot(this);
			iLoader.setController(this);

			iLoader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	public String getHref() {
		return this.href;
	}

	@Override
	public void handle(final ActionEvent aEvent) {
		final ExtFXMLLoader iParentLoader = ExtFXMLLoader.loaders.get(this.getParent().getParent());

		final TabPane iTabPane = (TabPane) iParentLoader.getNamespace().get("$tabpane");
		iTabPane.getTabs().add(new ExtendedTab(iParentLoader, this.href));
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.setOnAction(this);
	}

	public void setHref(final String aHref) {
		this.href = aHref;
	}

}
