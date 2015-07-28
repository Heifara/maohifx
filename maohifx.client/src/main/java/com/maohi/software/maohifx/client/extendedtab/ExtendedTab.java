/**
 * 
 */
package com.maohi.software.maohifx.client.extendedtab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * @author heifara
 *
 */
public class ExtendedTab extends Tab implements Initializable, ChangeListener<TabPane> {

	private final FXMLLoader loader;
	private Tab selectedTab;
	private TabPane parent;

	public ExtendedTab() {
		this.loader = new FXMLLoader(this.getClass().getResource("ExtendedTab.fxml"));
		this.loader.setRoot(this);
		this.loader.setController(this);

		this.tabPaneProperty().addListener(this);

		try {
			this.loader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	@Override
	public void changed(final ObservableValue<? extends TabPane> aObservable, final TabPane aOldValue, final TabPane aNewValue) {
		if (this.parent == null) {
			this.parent = aNewValue;
			this.selectedTab = this.parent.getSelectionModel().getSelectedItem();
			this.parent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> aObservable, Tab aOldValue, Tab aNewValue) {
					selectedTab = aNewValue;
				}
			});
		}
	}

	@FXML
	public void closeTabEvent(final ActionEvent aEvent) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				parent.getTabs().remove(selectedTab);
			}
		});
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.setText("Nouvelle Onglet");
	}

	@FXML
	public void newTabEvent(final ActionEvent aEvent) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				parent.getTabs().add(new ExtendedTab());
			}
		});
	}

}
