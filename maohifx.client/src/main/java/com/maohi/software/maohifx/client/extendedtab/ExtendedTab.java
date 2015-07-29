/**
 * 
 */
package com.maohi.software.maohifx.client.extendedtab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.StatusBar;

import com.maohi.software.maohifx.client.ExtFXMLLoader;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * @author heifara
 *
 */
public class ExtendedTab extends Tab implements Initializable, ChangeListener<TabPane> {

	private final ExtFXMLLoader loader;
	private Tab selectedTab;
	private TabPane parent;

	private boolean refreshing;

	@FXML
	private TextField url;

	@FXML
	private BorderPane content;

	@FXML
	private MenuButton menuButton;

	@FXML
	private MenuItem hidShowUrl;

	@FXML
	private StatusBar statusBar;

	public ExtendedTab(final ExtFXMLLoader aParent) {
		try {
			this.loader = aParent.getLoader(this.getClass().getResource("ExtendedTab.fxml"));
			this.loader.setRoot(this);
			this.loader.setController(this);

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
				public void changed(final ObservableValue<? extends Tab> aObservable, final Tab aOldValue, final Tab aNewValue) {
					ExtendedTab.this.selectedTab = aNewValue;
				}
			});
		}
	}

	@FXML
	public void closeTabEvent(final ActionEvent aEvent) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExtendedTab.this.parent.getTabs().remove(ExtendedTab.this.selectedTab);
			}
		});
	}

	public void homeEvent(final ActionEvent aEvent) {
		this.url.setText("http://localhost:8080/maohifx.server/webapi/fxml?id=invoices");
		this.refreshTabEvent(aEvent);
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.tabPaneProperty().addListener(this);

		this.setText("Nouvelle Onglet");

		this.setGraphic(this.menuButton);
	}

	@FXML
	public void newTabEvent(final ActionEvent aEvent) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExtendedTab.this.parent.getTabs().add(new ExtendedTab(ExtendedTab.this.loader));
			}
		});
	}

	@FXML
	public void refreshTabEvent(final ActionEvent aEvent) {
		if (!this.refreshing && !ExtendedTab.this.url.getText().isEmpty()) {
			final ProgressIndicator iProgressBar = new ProgressIndicator();
			this.statusBar.getRightItems().add(iProgressBar);

			this.refreshing = true;

			this.content.setCenter(null);

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						final ExtFXMLLoader iLoader = ExtendedTab.this.loader.getLoader(ExtendedTab.this.url.getText());
						iLoader.getNamespace().put("$tab", ExtendedTab.this);
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									ExtendedTab.this.content.setCenter(iLoader.load());
								} catch (final Exception aException) {
									final Label iLabel = new Label();
									iLabel.setText(aException.getMessage());
									ExtendedTab.this.content.setCenter(iLabel);
								}

								ExtendedTab.this.statusBar.getRightItems().remove(iProgressBar);
								ExtendedTab.this.refreshing = false;
							}
						});
					} catch (final IOException aException) {
						final Label iLabel = new Label();
						iLabel.setText(aException.getMessage());
						ExtendedTab.this.content.setCenter(iLabel);
					}
				}
			}).start();
		}
	}

	@FXML
	public void showHideUrl(final ActionEvent aEvent) {
		if (this.url.isVisible()) {
			this.url.setVisible(false);
			this.hidShowUrl.setText("Afficher la barre d'adresse");
		} else {
			this.url.setVisible(true);
			this.hidShowUrl.setText("Masquer la barre d'adresse");
		}
	}

}
