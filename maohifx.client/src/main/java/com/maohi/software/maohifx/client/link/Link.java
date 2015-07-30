/**
 * 
 */
package com.maohi.software.maohifx.client.link;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.maohi.software.maohifx.client.ExtFXMLLoader;
import com.maohi.software.maohifx.client.extendedtab.ExtendedTab;

import javafx.collections.FXCollections;
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

	private Map<String, Object> namespace = FXCollections.observableHashMap();

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

	public Map<String, Object> getNamespace() {
		return this.namespace;
	}

	@Override
	public void handle(final ActionEvent aEvent) {
		try {
			final ExtFXMLLoader iParentLoader = ExtFXMLLoader.loaders.get(this.getParent().getParent());

			final TabPane iTabPane = (TabPane) iParentLoader.getNamespace().get("$tabpane");

			ExtFXMLLoader iLoader = iParentLoader.getLoader(this.href);
			for (String iKey : namespace.keySet()) {
				iLoader.getNamespace().put(iKey, namespace.get(iKey));
			}
			iTabPane.getTabs().add(new ExtendedTab(iLoader, this.href));
		} catch (MalformedURLException aException) {
			aException.printStackTrace();
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.setOnAction(this);
	}

	public void setHref(final String aHref) {
		this.href = aHref;
	}

	public void setNamespace(final Map<String, Object> namespace) {
		this.namespace = namespace;
	}

}
