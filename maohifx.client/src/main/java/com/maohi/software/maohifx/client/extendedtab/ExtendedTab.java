/**
 *
 */
package com.maohi.software.maohifx.client.extendedtab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.maohi.software.maohifx.client.rest.RestManagerImpl;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author heifara
 *
 */
public class ExtendedTab extends Tab implements Initializable, ChangeListener<TabPane> {

	private final FXMLLoader loader;
	private Tab selectedTab;
	private TabPane parent;

	private boolean refreshing;

	@FXML
	private TextField url;

	@FXML
	private BorderPane content;

	@FXML
	private MenuItem hidShowUrl;

	@FXML
	private MenuButton menuButton;

	@FXML
	private ProgressIndicator progressIndicator;

	@FXML
	private Button refreshButton;

	@FXML
	private Button homeButton;

	@FXML
	private VBox urlPane;

	public ExtendedTab(final FXMLLoader aParent) {
		try {
			this.loader = new FXMLLoader();
			this.loader.setLocation(this.getClass().getResource("ExtendedTab.fxml"));
			this.loader.setRoot(this);
			this.loader.setController(this);
			this.loader.getNamespace().put("$parentLoader", aParent);
			for (final String iKey : aParent.getNamespace().keySet()) {
				this.loader.getNamespace().put(iKey, aParent.getNamespace().get(iKey));
			}

			this.loader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	public ExtendedTab(final FXMLLoader aParent, final String aUrl) {
		this(aParent);

		this.setUrl(aUrl);
		this.refreshTabEvent(new ActionEvent());
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
		this.getStyleClass().add("extended-tab");

		this.tabPaneProperty().addListener(this);

		this.setText("Nouvelle Onglet");

		this.menuButton.setBorder(null);
		this.menuButton.setBackground(null);

		this.refreshButton.setBorder(null);
		this.refreshButton.setBackground(null);

		this.homeButton.setBorder(null);
		this.homeButton.setBackground(null);

		this.urlPane.getStyleClass().add("vbox");
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

	public void openEvent(final ActionEvent aEvent) {
		this.newTabEvent(aEvent);
	}

	@FXML
	public void refreshTabEvent(final ActionEvent aEvent) {
		if (!this.refreshing && !ExtendedTab.this.url.getText().isEmpty()) {

			this.refreshing = true;

			this.progressIndicator.setVisible(true);

			this.content.setCenter(null);

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						final FXMLLoader iLoader = new FXMLLoader(new URL(ExtendedTab.this.url.getText()));
						iLoader.getNamespace().put("$loader", iLoader);
						iLoader.getNamespace().put("$tab", ExtendedTab.this);
						iLoader.getNamespace().put("$tabpane", ExtendedTab.this.parent);
						iLoader.getNamespace().put("$http", new RestManagerImpl(iLoader));
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								try {
									final Node iNode = iLoader.load();
									ExtendedTab.this.content.setCenter(iNode);

								} catch (final Exception aException) {
									final Label iLabel = new Label();
									iLabel.setText(aException.getMessage());
									ExtendedTab.this.content.setCenter(iLabel);
									aException.printStackTrace();
								}

								ExtendedTab.this.refreshing = false;
								ExtendedTab.this.progressIndicator.setVisible(false);
							}
						});
					} catch (final IOException aException) {
						final Label iLabel = new Label();
						iLabel.setText(aException.getMessage());
						ExtendedTab.this.content.setCenter(iLabel);
						aException.printStackTrace();
					}
				}
			}).start();
		}
	}

	@FXML
	public void selectTabEvent() {
		this.parent.getSelectionModel().select(this);
	}

	public void setUrl(final String aUrl) {
		this.url.setText(aUrl);
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
