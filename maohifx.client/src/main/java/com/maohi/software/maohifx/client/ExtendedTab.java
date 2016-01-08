/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.maohi.software.maohifx.client.event.AuthentificationEvent;
import com.maohi.software.maohifx.client.event.ConnectEvent;
import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.event.SuccesEvent;
import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.common.Files;
import com.maohi.software.maohifx.common.Profile;
import com.maohi.software.maohifx.control.Link;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ExtendedTab extends Tab implements Initializable, ListChangeListener<String> {

	private final MaohiFXView view;
	private final MaohiFXController controller;

	private Parent refreshTarget;
	private String refreshText;
	private Thread runningThread;

	private EventHandler<Event> onStart;
	private EventHandler<AuthentificationEvent> onAuthentification;
	private EventHandler<SuccesEvent> onSucces;
	private EventHandler<Event> onEnd;
	private EventHandler<ConnectEvent> onConnectSucces;
	private EventHandler<ConnectEvent> onConnectError;

	private EventHandler<ExceptionEvent> onExceptionThrown;
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

	public ExtendedTab(final MaohiFXView aView) {
		this.view = aView;
		this.controller = this.view.getController();
		this.controller.addEventHandler(ConnectEvent.CONNECT_SUCCES, this.getOnConnectSucces());
		this.controller.addEventHandler(ConnectEvent.CONNECT_ERROR, this.getOnConnectError());

		try {
			final FXMLLoader iLoader = new FXMLLoader();
			iLoader.setBuilderFactory(this.view.getBuilderFactory());
			iLoader.setLocation(this.getClass().getResource("ExtendedTab.fxml"));
			iLoader.setRoot(this);
			iLoader.setController(this);
			iLoader.load();

			this.getStyleClass().add("extended-tab");
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	@FXML
	public void closeTabEvent(final ActionEvent aEvent) {
		this.view.closeCurrentTab();
	}

	@FXML
	public void configEvent(final ActionEvent aEvent) {
		final Stage iOwner = (Stage) this.getTabPane().getScene().getWindow();

		final Stage iDialog = new Stage(iOwner.getStyle());
		iDialog.getIcons().add(new Image("config.png"));
		iDialog.setTitle("Préférences");
		iDialog.initOwner(iOwner);
		iDialog.initModality(Modality.WINDOW_MODAL);

		final Scene iScene = new Scene(new ConfigPane(this.view), 400, 300);
		iScene.getStylesheets().add("MaohiFXClient.css");
		iDialog.setScene(iScene);

		iDialog.show();
	}

	@FXML
	public void connectEvent(final ActionEvent aEvent) {
		if (this.controller.isConnected()) {
			final PopOver iPopOver = new PopOver();
			iPopOver.setDetachable(true);
			iPopOver.setArrowSize(10.0);
			iPopOver.setArrowLocation(ArrowLocation.TOP_RIGHT);

			final Link iLink = new Link("Se déconnecter");
			iLink.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(final ActionEvent aEvent) {
					ExtendedTab.this.controller.disconnect();
					iPopOver.hide();
				}
			});
			iPopOver.setContentNode(iLink);

			iPopOver.show(this.profileButton);
		} else {
			final PopOver iPopOver = new PopOver();
			iPopOver.setDetachable(true);
			iPopOver.setContentNode(new LoginPane(this.view));
			iPopOver.setArrowSize(10.0);
			iPopOver.setArrowLocation(ArrowLocation.TOP_RIGHT);
			iPopOver.show(this.profileButton);
		}
	}

	protected void displayException(final Throwable aException) {
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

	protected void displayFile(final File iFile) {
		final String iText = Files.toString(iFile);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				final TextArea iStackTrace = new TextArea();
				iStackTrace.setText(iText);
				iStackTrace.setEditable(false);
				ExtendedTab.this.content.setCenter(iStackTrace);
			}
		});
	}

	protected void displayHtml(final URL aUrl) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				final Browser iBrowser = new Browser();
				iBrowser.load(aUrl.toString());
				ExtendedTab.this.content.setCenter(iBrowser);
			}
		});
	}

	protected void displayImage(final Image aImage) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExtendedTab.this.content.setCenter(new ImageView(aImage));
			}
		});
	}

	protected void displayNode(final FXMLLoader iLoader, final Parent aTarget, final String aText) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					iLoader.setBuilderFactory(ExtendedTab.this.view.getBuilderFactory());
					iLoader.getNamespace().put("$tab", ExtendedTab.this);
					iLoader.getNamespace().put("$menuButton", ExtendedTab.this.menuButton);

					final HttpHandler iHandler = new HttpHandler(iLoader.getLocation());
					iHandler.setOnStart(ExtendedTab.this.getOnStart());
					iHandler.setOnAuthentification(ExtendedTab.this.onAuthentification);
					iHandler.setOnEnd(ExtendedTab.this.getOnEnd());
					iLoader.getNamespace().put("$http", iHandler);

					ExtendedTab.this.view.populateNamespace(iLoader);

					if (aTarget instanceof BorderPane) {
						final BorderPane iBorderPane = (BorderPane) aTarget;
						iBorderPane.setCenter(iLoader.load());

					} else if (aTarget instanceof TabPane) {
						final Tab iTab = new Tab();
						iTab.setText(aText);
						iTab.setClosable(true);
						iTab.setContent(iLoader.load());

						final TabPane iTabPane = (TabPane) aTarget;
						iTabPane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
						iTabPane.getTabs().add(iTab);
						iTabPane.getSelectionModel().select(iTabPane.getTabs().indexOf(iTab));
					} else {
						throw new IllegalArgumentException();
					}
				} catch (final Exception aException) {
					ExtendedTab.this.displayException(aException);
				}
			}

		});
	}

	protected void displayRunning(final boolean aRunning) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ExtendedTab.this.progressIndicator.setVisible(aRunning);
				if (aRunning) {
					ExtendedTab.this.refreshButton.setId("stop");
				} else {
					ExtendedTab.this.refreshButton.setId("refresh");
				}
			}
		});
	}

	protected void displayText(final String aText) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				final TextArea iStackTrace = new TextArea();
				iStackTrace.setText(aText);
				iStackTrace.setEditable(false);
				ExtendedTab.this.content.setCenter(iStackTrace);
			}
		});
	}

	public EventHandler<AuthentificationEvent> getOnAuthentification() {
		if (this.onAuthentification == null) {
			this.onAuthentification = new EventHandler<AuthentificationEvent>() {

				@Override
				public void handle(final AuthentificationEvent aEvent) {
					final Profile iProfile = ExtendedTab.this.controller.getProfile();
					if (iProfile != null) {
						aEvent.getWebTarget().register(HttpAuthenticationFeature.basic(iProfile.getToken(), iProfile.getRole()));
					}
				}
			};
		}
		return this.onAuthentification;
	}

	public EventHandler<ConnectEvent> getOnConnectError() {
		if (this.onConnectError == null) {
			this.onConnectError = new EventHandler<ConnectEvent>() {

				@Override
				public void handle(final ConnectEvent aEvent) {
				}
			};
		}
		return this.onConnectError;
	}

	public EventHandler<ConnectEvent> getOnConnectSucces() {
		if (this.onConnectSucces == null) {
			this.onConnectSucces = new EventHandler<ConnectEvent>() {

				@Override
				public void handle(final ConnectEvent aEvent) {
					ExtendedTab.this.profileConnected();
				}
			};
		}
		return this.onConnectSucces;
	}

	public EventHandler<Event> getOnEnd() {
		if (this.onEnd == null) {
			this.onEnd = new EventHandler<Event>() {

				@Override
				public void handle(final Event aEvent) {
					ExtendedTab.this.displayRunning(false);
				}
			};
		}
		return this.onEnd;
	}

	public EventHandler<ExceptionEvent> getOnExceptionThrown() {
		if (this.onExceptionThrown == null) {
			this.onExceptionThrown = new EventHandler<ExceptionEvent>() {

				@Override
				public void handle(final ExceptionEvent aEvent) {
					ExtendedTab.this.displayException(aEvent.getException());
				}
			};
		}
		return this.onExceptionThrown;

	}

	public EventHandler<Event> getOnStart() {
		if (this.onStart == null) {
			this.onStart = new EventHandler<Event>() {

				@Override
				public void handle(final Event aEvent) {
					ExtendedTab.this.displayRunning(true);
				}
			};
		}
		return this.onStart;
	}

	public EventHandler<SuccesEvent> getOnSucces() {
		if (this.onSucces == null) {
			this.onSucces = new EventHandler<SuccesEvent>() {

				@Override
				public void handle(final SuccesEvent aEvent) {
					final Object iItem = aEvent.getItem();
					if (iItem instanceof File) {
						final File iFile = (File) iItem;
						if (iFile.getName().endsWith(".pdf")) {
							try {
								Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + iFile);
							} catch (final IOException aException) {
								aException.printStackTrace();
							}
						} else if (iFile.getName().endsWith(".html")) {
							ExtendedTab.this.displayHtml(aEvent.getUrl());
						} else if (iFile.getName().endsWith(".xml")) {
							ExtendedTab.this.displayFile(iFile);
						} else if (iFile.getName().endsWith(".txt")) {
							ExtendedTab.this.displayFile(iFile);
						}
					} else if (iItem instanceof Image) {
						ExtendedTab.this.displayImage((Image) iItem);
					} else if (aEvent.isFxml()) {
						final FXMLLoader iLoader = new FXMLLoader(aEvent.getProcessesdUrl());
						if (aEvent.getItem() != null) {
							iLoader.getNamespace().put("$item", aEvent.getItem());
						}
						ExtendedTab.this.displayNode(iLoader, ExtendedTab.this.refreshTarget, ExtendedTab.this.refreshText);

						final URL iUrl = aEvent.getUrl();
						if (!ExtendedTab.this.urlAutoCompletion.contains(iUrl.toExternalForm())) {
							ExtendedTab.this.urlAutoCompletion.add(iUrl.toExternalForm());
						}
					} else if (aEvent.getContentType().equals(MediaType.APPLICATION_JSON)) {
						ExtendedTab.this.displayText(iItem.toString());
					}
				}
			};
		}
		return this.onSucces;
	}

	public void homeEvent(final ActionEvent aEvent) {
		this.url.textProperty().set(this.controller.getConfiguration().getHomeUrl());
		this.refreshTabEvent(aEvent);
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.profileConnected();
		this.initUrlAutoCompletion();
	}

	private void initUrlAutoCompletion() {
		final Configuration iConfiguration = this.controller.getConfiguration();
		if (iConfiguration.getHistoryUrl() != null) {
			for (final String iUrl : iConfiguration.getHistoryUrl().getUrl()) {
				this.urlAutoCompletion.add(iUrl);
			}
		}

		this.urlAutoCompletion.addListener(this);
	}

	@FXML
	public void newTabEvent(final ActionEvent aEvent) {
		this.view.newTab();
	}

	@Override
	public void onChanged(final javafx.collections.ListChangeListener.Change<? extends String> aChange) {
		final Configuration iConfiguration = this.controller.getConfiguration();
		iConfiguration.getHistoryUrl().getUrl().clear();
		iConfiguration.getHistoryUrl().getUrl().addAll(this.urlAutoCompletion);
		this.controller.save(iConfiguration);
	}

	protected void profileConnected() {
		if (this.controller.isConnected()) {
			this.profileButton.setText(this.controller.getProfile().getUsername());
		} else {
			this.profileButton.setText("Se connecter");
		}
	}

	public void refreshTab(final String aText) {
		this.refreshTab(this.url.getText(), this.content, aText);
	}

	public void refreshTab(final String aUrl, final Parent aTarget, final String aText) {
		this.refreshTarget = aTarget;
		this.refreshText = aText;

		this.runningThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					final URLHandler iUrlHandler = new URLHandler();
					iUrlHandler.setOnStart(ExtendedTab.this.getOnStart());
					iUrlHandler.setOnAuthentification(ExtendedTab.this.getOnAuthentification());
					iUrlHandler.setOnSucces(ExtendedTab.this.getOnSucces());
					iUrlHandler.setOnEnd(ExtendedTab.this.getOnEnd());
					iUrlHandler.setOnExceptionThrown(ExtendedTab.this.getOnExceptionThrown());

					iUrlHandler.process(new URL(aUrl), "get", null);
				} catch (final InterruptedException aException) {
					System.err.println(aException.getMessage());
				} catch (final IOException aException) {
					ExtendedTab.this.displayException(aException);
				}
			}
		});
		this.runningThread.setName(this.url.getText());
		this.runningThread.start();
	}

	public void refreshTab(final String aUrl, final String aText) {
		this.refreshTab(aUrl, this.content, aText);
	}

	@FXML
	public void refreshTabEvent(final ActionEvent aEvent) {
		if ((this.runningThread != null) && (this.runningThread.isAlive() && !this.runningThread.isInterrupted())) {
			this.displayRunning(false);
			this.runningThread.interrupt();
		} else {
			if ((this.url.textProperty() != null) && (this.url.textProperty().get() != null) && !this.url.textProperty().get().isEmpty()) {

				this.content.setCenter(null);

				this.refreshTab(this.url.getText(), this.content, "");
			}
		}
	}

	@FXML
	public void selectTabEvent() {
		this.getTabPane().getSelectionModel().select(this);
	}

	public void setIcon(final String aUrl) {
		String iUrl = aUrl;
		if (iUrl.startsWith("@/")) {
			iUrl = iUrl.replace("@/", "@");
		}
		if (iUrl.startsWith("@")) {
			final StringBuilder iBaseUrl = new StringBuilder();
			iBaseUrl.append("http");
			iBaseUrl.append("://");
			iBaseUrl.append("localhost");
			iBaseUrl.append(":");
			iBaseUrl.append("8080");
			iBaseUrl.append("/");

			iUrl = iUrl.replace("@", iBaseUrl.toString());
		}

		this.menuButton.setGraphic(new ImageView(new Image(iUrl)));
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
