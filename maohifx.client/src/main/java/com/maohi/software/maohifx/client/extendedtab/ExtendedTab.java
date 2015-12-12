/**
 *
 */
package com.maohi.software.maohifx.client.extendedtab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author heifara
 *
 */
public class ExtendedTab extends Tab implements Initializable, ChangeListener<TabPane>, Runnable {

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
			this.loader.setBuilderFactory(aParent.getBuilderFactory());
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
		this.url.setText("fxml://localhost:8080/maohifx.server/webapi/index");
		this.refreshTabEvent(aEvent);
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.getStyleClass().add("extended-tab");

		this.tabPaneProperty().addListener(this);

		this.setText("Nouvelle Onglet");

		this.menuButton.setBorder(null);
		this.menuButton.setBackground(null);
	}

	public void load(final FXMLLoader aLoader, final String aText, final String aURL, final String aRecipeId) {
		aLoader.setBuilderFactory(this.loader.getBuilderFactory());
		this.populateLoaderNamespace(aLoader, true, true);

		final Node iRecipee = this.getContent().lookup(aRecipeId);

		try {
			final URL iUrl = this.toHttp(new URL(aURL), true);
			aLoader.setLocation(iUrl);
			final Node iNode = aLoader.load();
			this.load(iRecipee, aText, iNode);
		} catch (final Exception aException) {
			this.load(iRecipee, aText, aException.getMessage());
		}

	}

	private void load(final Node aRecipee, final String aText, final Object aObject) {
		if (aRecipee instanceof BorderPane) {
			final BorderPane iBorderPane = (BorderPane) aRecipee;

			if (aObject instanceof String) {
				iBorderPane.setCenter(new Label((String) aObject));
			} else if (aObject instanceof Node) {
				iBorderPane.setCenter((Node) aObject);
			} else {
				throw new IllegalArgumentException();
			}
		} else if (aRecipee instanceof TabPane) {
			final Tab iTab = new Tab();
			iTab.setText(aText);
			iTab.setClosable(true);

			if (aObject instanceof String) {
				iTab.setContent(new Label((String) aObject));
			} else if (aObject instanceof Node) {
				iTab.setContent((Node) aObject);
			} else {
				throw new IllegalArgumentException();
			}

			final TabPane iTabPane = (TabPane) aRecipee;
			iTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
			iTabPane.getTabs().add(iTab);
			iTabPane.getSelectionModel().select(iTabPane.getTabs().indexOf(iTab));
		} else {
			throw new IllegalArgumentException();
		}
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

	private void populateLoaderNamespace(final FXMLLoader aLoader, final boolean aIgnoreTab, final boolean aIgnoreTabPane) {
		final FXMLLoader iParentLoader = (FXMLLoader) this.loader.getNamespace().get("$parentLoader");
		if (iParentLoader != null) {
			for (final String iKey : iParentLoader.getNamespace().keySet()) {
				aLoader.getNamespace().put(iKey, iParentLoader.getNamespace().get(iKey));
			}
		}

		aLoader.getNamespace().put("$loader", aLoader);

		if (!aIgnoreTab) {
			aLoader.getNamespace().put("$tab", ExtendedTab.this);
		}
		if (!aIgnoreTabPane) {
			aLoader.getNamespace().put("$tabpane", ExtendedTab.this.parent);
		}
		aLoader.getNamespace().put("$http", new RestManagerImpl(aLoader));
	}

	@FXML
	public void refreshTabEvent(final ActionEvent aEvent) {
		if (!this.refreshing && !ExtendedTab.this.url.getText().isEmpty()) {

			this.refreshing = true;

			this.progressIndicator.setVisible(true);

			this.content.setCenter(null);

			new Thread(this).start();
		}
	}

	@Override
	public void run() {
		try {
			final URL iUrl = new URL(ExtendedTab.this.url.getText());
			final FXMLLoader iLoader = new FXMLLoader();
			iLoader.setBuilderFactory(this.loader.getBuilderFactory());
			if ((iUrl.getQuery() != null) && !iUrl.getQuery().isEmpty()) {
				final Client iClient = ClientBuilder.newClient();
				final WebTarget iTarget = iClient.target(this.toHttp(iUrl, false).toString()).queryParam(iUrl.getQuery().split("=")[0], iUrl.getQuery().split("=")[1]);
				final Response iResponse = iTarget.request().get();

				final int iStatus = iResponse.getStatus();

				switch (Status.fromStatusCode(iStatus)) {
				case OK:
					switch (iResponse.getHeaderString("Content-Type")) {
					case MediaType.APPLICATION_JSON:
						final Object iObject = iResponse.readEntity(Object.class);
						iLoader.getNamespace().put("$item", iObject);
						break;

					default:
						throw new Exception(iResponse.readEntity(String.class));
					}
					break;

				default:
					break;
				}

			}
			this.populateLoaderNamespace(iLoader, false, false);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					try {
						iLoader.setLocation(ExtendedTab.this.toHttp(iUrl, true));
						final Node iNode = iLoader.load();
						ExtendedTab.this.content.setCenter(iNode);
					} catch (final IOException aException) {
						final Label iLabel = new Label();
						iLabel.setText(aException.getMessage());
						ExtendedTab.this.content.setCenter(iLabel);
						aException.printStackTrace();
					}
				}
			});

			ExtendedTab.this.refreshing = false;
			ExtendedTab.this.progressIndicator.setVisible(false);

		} catch (final Exception aException) {
			final Label iLabel = new Label();
			iLabel.setText(aException.getMessage());
			ExtendedTab.this.content.setCenter(iLabel);
			aException.printStackTrace();
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

	public URL toHttp(final URL iUrl, final boolean aForFXMLLoader) throws MalformedURLException {
		final StringBuilder iURLBuilder = new StringBuilder();
		iURLBuilder.append("http://");
		iURLBuilder.append(iUrl.getHost());
		iURLBuilder.append(iUrl.getPort() != 0 ? ":" + iUrl.getPort() : "");

		if (aForFXMLLoader) {
			final String iBasePath = "/maohifx.server/webapi";
			final String iId = iUrl.getPath().substring(iBasePath.length(), iUrl.getPath().length()).replaceAll("/", "");
			iURLBuilder.append(iBasePath);
			iURLBuilder.append("/fxml?id=" + iId);
		} else {
			iURLBuilder.append(iUrl.getPath());
		}

		return new URL(iURLBuilder.toString());
	}

}
