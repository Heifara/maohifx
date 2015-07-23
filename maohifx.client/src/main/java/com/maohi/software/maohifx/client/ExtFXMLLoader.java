/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * @author heifara
 *
 */
public class ExtFXMLLoader extends FXMLLoader {

	private final FXMLLoader parent;

	public ExtFXMLLoader() {
		this.parent = null;

		this.getNamespace().put("$loader", this);
	}

	protected ExtFXMLLoader(FXMLLoader aParent) {
		this.parent = aParent;

		this.getNamespace().put("$loader", this);
	}

	/**
	 * Return a loader with the parent's location
	 * 
	 * @return a loader
	 * @throws MalformedURLException
	 */
	public ExtFXMLLoader getLoader() throws MalformedURLException {
		return getLoader(this.parent.getLocation());
	}

	/**
	 * Return a loader with <b>aUrl</b> as location
	 * 
	 * @param aUrl
	 *            the url to load
	 * @return
	 * @throws MalformedURLException
	 */
	public ExtFXMLLoader getLoader(String aUrl) throws MalformedURLException {
		return getLoader(new URL(aUrl));
	}

	/**
	 * Return a new {@link ExtFXMLLoader} with the namespace of its parent
	 * 
	 * @return the new loader
	 * @throws MalformedURLException
	 */
	public ExtFXMLLoader getLoader(URL aUrl) throws MalformedURLException {
		ExtFXMLLoader iChildLoader = new ExtFXMLLoader(this);
		iChildLoader.setLocation(aUrl);

		// Give all namespace to child and replace $loader
		for (String iKey : getNamespace().keySet()) {
			iChildLoader.getNamespace().put(iKey, getNamespace().get(iKey));
		}
		// Replace $loader by the child
		iChildLoader.getNamespace().put("$loader", iChildLoader);
		iChildLoader.getNamespace().put("$parentLoader", this);

		return iChildLoader;
	}

	/**
	 * Create a new {@link Tab} to add in <b>aTabPane</b>. <br>
	 * The new {@link Tab} is loaded with the location<br>
	 * And contains $tab and $url namespaces.<br>
	 * The loaded Fxml is responsible for handling $url
	 * 
	 * @param aTabPane
	 *            the tabpane
	 * @param aUrl
	 *            the url as in $url
	 * @throws IOException
	 */
	public void load(TabPane aTabPane, String aUrl) throws IOException {
		Tab iTab = new Tab();
		this.getNamespace().put("$tab", iTab);
		this.getNamespace().put("$url", aUrl);
		iTab.setContent(this.load());
		aTabPane.getTabs().add(iTab);
		aTabPane.getSelectionModel().select(aTabPane.getTabs().indexOf(iTab));
	}
}
