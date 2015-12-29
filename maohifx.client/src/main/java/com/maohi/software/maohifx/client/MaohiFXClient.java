/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.controlsfx.dialog.Dialogs;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.jaxb2.Configuration.HistoryUrl;
import com.maohi.software.maohifx.common.JaxbUtils;
import com.maohi.software.maohifx.control.Link;
import com.maohi.software.maohifx.control.Link.LinkTarget;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;

/**
 * @author heifara
 *
 */
public class MaohiFXClient extends Application implements ListChangeListener<Tab>, ChangeListener<Tab>, LinkTarget {

	@Option(name = "-config", usage = "path to the cofig file")
	private String config;

	private final FXMLLoader loader;
	private Stage stage;
	private Scene scene;
	private BorderPane mainPane;
	private TabPane tabpane;
	private ExtendedTab currentTab;
	private Configuration configuration;

	private StringProperty profile;

	public MaohiFXClient() {
		this.loader = new FXMLLoader();
		this.loader.setBuilderFactory(new MaohiFXBuilderFactory());
		this.loader.setLocation(this.getClass().getResource("MaohiFXClient.fxml"));
	}

	@Override
	public void changed(final ObservableValue<? extends Tab> aObservable, final Tab aOldValue, final Tab aNewValue) {
		this.currentTab = (ExtendedTab) aNewValue;
	}

	public boolean connect(final String aUsername, final String aPasswd) {
		if (aUsername.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Username can not be null");
			return false;
		}

		this.setProfile(aUsername);
		return true;
	}

	@Override
	public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
		JOptionPane.showMessageDialog(null, aException.getMessage());
	}

	public BuilderFactory getBuilderFactory() {
		return this.loader.getBuilderFactory();
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public FXMLLoader getLoader() {
		return this.loader;
	}

	public String getProfile() {
		return this.profileProperty().get();
	}

	@Override
	public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final String aRecipeeId) {
		switch (aLink.getTarget()) {
		case BLANK:
			this.tabpane.getTabs().add(new ExtendedTab(this, this.loader, aLink.getHref()));
			break;

		case SELF:
			this.currentTab.setUrl(aLink.getHref());
			this.currentTab.refreshTabEvent(aEvent);
			break;

		case FRAMENAME:
			this.currentTab.load(aLoader, aLink.getText(), aLink.getHref(), aRecipeeId);

			break;

		default:
			break;
		}
	}

	@Override
	public void init() throws Exception {
		super.init();
	}

	public boolean isConnected() {
		if (this.getProfile() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onChanged(final javafx.collections.ListChangeListener.Change<? extends Tab> aChange) {
		if (aChange.next()) {
			if (aChange.wasRemoved()) {
				if (MaohiFXClient.this.tabpane.getTabs().size() == 0) {
					MaohiFXClient.this.stage.close();
				}
			} else if (aChange.wasAdded()) {
				final List<? extends Tab> iSelectedItems = aChange.getAddedSubList();
				final Tab iTab = iSelectedItems.get(0);
				this.tabpane.getSelectionModel().select(iTab);
			} else if (aChange.wasReplaced()) {
			} else if (aChange.wasUpdated()) {
			} else if (aChange.wasPermutated()) {
			}
		}
	}

	public StringProperty profileProperty() {
		if (this.profile == null) {
			this.profile = new SimpleStringProperty();
		}
		return this.profile;
	}

	private void readConfiguration() throws FileNotFoundException, JAXBException {
		final InputStream iInputStream = new FileInputStream(this.config);
		if (iInputStream != null) {
			this.configuration = (Configuration) JaxbUtils.readXML(iInputStream, "com.maohi.software.maohifx.client.jaxb2", this.getClass().getClassLoader());
			if (this.configuration.getHistoryUrl() == null) {
				this.configuration.setHistoryUrl(new HistoryUrl());
			}
		}
	}

	public boolean saveConfiguration(final Configuration aConfiguration) {
		try {
			final File iConfigFile = new File(this.config);
			final OutputStream iOutputStream = new FileOutputStream(iConfigFile);
			JaxbUtils.writeXML(aConfiguration, iOutputStream, "com.maohi.software.maohifx.client.jaxb2", this.getClass().getClassLoader());

			this.readConfiguration();
			return true;
		} catch (final Exception aException) {
			Dialogs.create().owner(this.stage).title("Exception Dialog").masthead(aException.getClass().getSimpleName()).message("Ooops, there was an exception!").showException(aException);
		}

		return false;
	}

	private void setProfile(final String aProfile) {
		this.profileProperty().set(aProfile);
	}

	@Override
	public void start(final Stage aStage) {
		this.config = "config.xml";

		try {
			final CmdLineParser iParser = new CmdLineParser(this);
			iParser.parseArgument(this.getParameters().getRaw());

			this.readConfiguration();

			this.mainPane = this.loader.load();

			this.stage = aStage;
			this.stage.getIcons().add(new Image("app.png"));
			this.scene = new Scene(this.mainPane, 800, 600);
			this.scene.getStylesheets().add("MaohiFXClient.css");

			this.tabpane = (TabPane) this.mainPane.getCenter();
			this.tabpane.getSelectionModel().selectedItemProperty().addListener(this);
			this.tabpane.getTabs().add(new ExtendedTab(this, this.loader));
			this.tabpane.getTabs().addListener(this);

			this.loader.getNamespace().put("$stage", this.stage);
			this.loader.getNamespace().put("$mainPane", this.mainPane);
			this.loader.getNamespace().put("$tabpane", this.tabpane);
			this.loader.getNamespace().put("$scene", this.scene);

			this.stage.setScene(this.scene);
			this.stage.show();

			Link.setHrefTarget(HrefTarget.BLANK, this);
			Link.setHrefTarget(HrefTarget.SELF, this);
			Link.setHrefTarget(HrefTarget.FRAMENAME, this);
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		} catch (final CmdLineException aException) {
			throw new RuntimeException(aException);
		} catch (final JAXBException aException) {
			throw new RuntimeException(aException);
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}
