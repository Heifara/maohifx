/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * @author heifara
 *
 */
public class ExtFXMLLoader extends FXMLLoader {

	/**
	 * The location of the fxml to load within the created Tab
	 * 
	 * @see ExtFXMLLoader#load(TabPane, String)
	 */
	private static String fxmlTabLocation = "http://localhost:8080/maohifx.server/webapi/fxml?id=newTab";

	/**
	 * The global namespace shared amongst loaded fxml
	 */
	private static ObservableMap<String, Object> globalNamespace = FXCollections.observableHashMap();;

	public static ObservableMap<String, Object> getGlobalNamespace() {
		return globalNamespace;
	}

	public static FXMLLoader getLoader(String aUrl) throws MalformedURLException {
		FXMLLoader iLoader = new FXMLLoader(new URL(aUrl));
		for (String iKey : globalNamespace.keySet()) {
			iLoader.getNamespace().put(iKey, globalNamespace.get(iKey));
		}
		return iLoader;
	}

	public static <T> T load(String aUrl) throws IOException {
		return getLoader(aUrl).load();
	}

	public static void load(TabPane aTabPane, String aUrl) throws IOException {
		Tab iTab = new Tab();
		FXMLLoader iLoader = getLoader(fxmlTabLocation);
		iLoader.getNamespace().put("$url", aUrl);
		iLoader.getNamespace().put("$tab", iTab);
		iTab.setContent(iLoader.load());
		aTabPane.getTabs().add(iTab);
		aTabPane.getSelectionModel().select(aTabPane.getTabs().indexOf(iTab));
	}
}
