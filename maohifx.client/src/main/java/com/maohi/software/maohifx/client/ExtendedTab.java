/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.rest.RestManagerImpl;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ExtendedTab extends Tab implements Initializable, ChangeListener<TabPane>, Runnable, ListChangeListener<String> {

	private final MaohiFXClient controller;
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

	@FXML
	private Button configButton;

	@FXML
	private Button profileButton;

	@FXML
	private ObservableList<String> urlAutoCompletion;

	public ExtendedTab(final MaohiFXClient aController, final FXMLLoader aParent) {
		this.controller = aController;

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

	public ExtendedTab(final MaohiFXClient aController, final FXMLLoader aParent, final String aUrl) {
		this(aController, aParent);

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

	@FXML
	public void configEvent(final ActionEvent aEvent) {
		final FXMLLoader iParentLoader = (FXMLLoader) this.loader.getNamespace().get("$parentLoader");
		final Stage iOwner = (Stage) iParentLoader.getNamespace().get("$stage");

		final Stage iDialog = new Stage(iOwner.getStyle());
		iDialog.setTitle("Préférences");
		iDialog.initOwner(iOwner);
		iDialog.initModality(Modality.WINDOW_MODAL);

		final Scene iScene = new Scene(new ConfigPane(this.controller, iParentLoader, iDialog), 400, 300);
		iScene.getStylesheets().add("MaohiFXClient.css");
		iDialog.setScene(iScene);

		iDialog.show();
	}

	@FXML
	public void connectEvent(final ActionEvent aEvent) {
		this.profileButton.setText("Heifara");
	}

	public void homeEvent(final ActionEvent aEvent) {
		final Configuration iConfiguration = this.controller.getConfiguration();
		this.url.setText(iConfiguration.getHomeUrl());
		this.refreshTabEvent(aEvent);
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.getStyleClass().add("extended-tab");

		this.tabPaneProperty().addListener(this);

		this.setText("Nouvelle Onglet");

		this.menuButton.setBorder(null);
		this.menuButton.setBackground(null);

		this.initProfile();

		this.initUrlAutoCompletion();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExtendedTab.this.url.requestFocus();
			}
		});
	}

	private void initProfile() {
		this.profileButton.setText("Se connecter");
	}

	private void initUrlAutoCompletion() {
		try {
			final InputStream iInputStream = this.getClass().getResourceAsStream("url.properties");
			if (iInputStream != null) {
				final Properties iProperties = new Properties();
				iProperties.load(iInputStream);

				if (iProperties.getProperty("url") != null) {
					for (final String iUrl : iProperties.getProperty("url").split(";")) {
						this.urlAutoCompletion.add(iUrl);
					}
				}
			}

		} catch (final IOException aException) {
			aException.printStackTrace();
		}

		this.urlAutoCompletion.addListener(this);
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
				ExtendedTab.this.parent.getTabs().add(new ExtendedTab(ExtendedTab.this.controller, ExtendedTab.this.loader));
			}
		});
	}

	@Override
	public void onChanged(final javafx.collections.ListChangeListener.Change<? extends String> aChange) {
		OutputStream iOutput = null;

		try {
			final URL iUrl = this.getClass().getResource("url.properties");
			if (iUrl != null) {
				final Properties iProperties = new Properties();
				iOutput = new FileOutputStream(new File(iUrl.getFile()));

				final StringBuilder iUrlBuilder = new StringBuilder();
				for (final String iUrlString : ExtendedTab.this.urlAutoCompletion) {
					iUrlBuilder.append(iUrlString);
					iUrlBuilder.append(";");
				}

				// set the properties value
				iProperties.setProperty("url", iUrlBuilder.toString());

				// save properties to project root folder
				iProperties.store(iOutput, null);
			}
		} catch (final IOException aException) {
			aException.printStackTrace();
		} finally {
			if (iOutput != null) {
				try {
					iOutput.close();
				} catch (final IOException aException) {
					aException.printStackTrace();
				}
			}

		}
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
				final WebTarget iTarget = iClient.target(iUrl.toExternalForm()).queryParam(iUrl.getQuery().split("=")[0], iUrl.getQuery().split("=")[1]);
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

				case INTERNAL_SERVER_ERROR:
					throw new InternalServerErrorException(iResponse);

				case NOT_FOUND:
					throw new NotFoundException(iResponse);

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

						if (!ExtendedTab.this.urlAutoCompletion.contains(iUrl.toExternalForm())) {
							ExtendedTab.this.urlAutoCompletion.add(iUrl.toExternalForm());
						}

						final Node iNode = iLoader.load();
						ExtendedTab.this.content.setCenter(iNode);
					} catch (final IOException aException) {
						final StringWriter iStringWriter = new StringWriter();
						final PrintWriter iPrintWriter = new PrintWriter(iStringWriter);
						aException.printStackTrace(iPrintWriter);

						final TextArea iStackTrace = new TextArea();
						iStackTrace.setText(iStringWriter.toString());
						iStackTrace.setEditable(false);
						ExtendedTab.this.content.setCenter(iStackTrace);
					}
				}
			});

			ExtendedTab.this.refreshing = false;
			ExtendedTab.this.progressIndicator.setVisible(false);

		} catch (final Exception aException) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					final StringWriter iStringWriter = new StringWriter();
					final PrintWriter iPrintWriter = new PrintWriter(iStringWriter);
					aException.printStackTrace(iPrintWriter);

					final TextArea iStackTrace = new TextArea();
					iStackTrace.setText(iStringWriter.toString());
					iStackTrace.setEditable(false);
					ExtendedTab.this.content.setCenter(iStackTrace);
				}
			});
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
		iURLBuilder.append(iUrl.getPath());
		if (!iUrl.getPath().endsWith("/")) {
			iURLBuilder.append("/");
		}
		if (!iUrl.getPath().endsWith(".fxml")) {
			iURLBuilder.append("index.fxml");
		}
		return new URL(iURLBuilder.toString().replace("webapi/", ""));
	}

}
