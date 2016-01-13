/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.jaxb2.Configuration.Authentication;
import com.maohi.software.maohifx.client.jaxb2.Configuration.Home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ConfigPane extends BorderPane implements Initializable {

	private final MaohiFXView view;
	private final FXMLLoader loader;

	@FXML
	private TextField homeUrl;

	@FXML
	private TextField authenticationServer;

	@FXML
	private CheckBox autoLoad;

	@FXML
	private TextField consoleMaxLines;

	@FXML
	private TextField consoleSystemMaxLines;

	@FXML
	private CheckBox consoleSystemEnable;

	public ConfigPane(final MaohiFXView aView) {
		this.view = aView;
		try {
			this.loader = new FXMLLoader();
			this.loader.setBuilderFactory(this.view.getBuilderFactory());
			this.loader.setLocation(this.getClass().getResource("ConfigPane.fxml"));
			this.loader.setRoot(this);
			this.loader.setController(this);
			this.loader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	@FXML
	public void applyEvent() {
		this.save();
	}

	@FXML
	public void cancelEvent() {
		final Stage iStage = (Stage) this.getScene().getWindow();
		iStage.close();
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		final Configuration iConfiguration = this.view.getController().getConfiguration();
		this.homeUrl.setText(iConfiguration.getHome().getUrl());
		this.authenticationServer.setText(iConfiguration.getAuthentication() != null ? iConfiguration.getAuthentication().getServer() : "");
		this.autoLoad.setSelected(iConfiguration.getHome().isAutoLoad());

		if (iConfiguration.getConsole() != null) {

			this.consoleMaxLines.setText(String.valueOf(iConfiguration.getConsole().getMaxLines()));

			this.consoleSystemMaxLines.setText(String.valueOf(iConfiguration.getConsole().getSystem().getMaxLines()));
			this.consoleSystemEnable.setSelected(iConfiguration.getConsole().getSystem().isEnableOnStartup());
		}
	}

	@FXML
	public void okEvent() {
		if (this.save()) {
			this.cancelEvent();
		}
	}

	private boolean save() {
		final Configuration iCurrentConfiguration = this.view.getController().getConfiguration();

		final Configuration iConfiguration = new Configuration();

		// Update Home URL
		iConfiguration.setHome(new Home());
		iConfiguration.setHome(iCurrentConfiguration.getHome());
		iConfiguration.getHome().setUrl(this.homeUrl.getText());
		iConfiguration.getHome().setAutoLoad(this.autoLoad.isSelected());

		// Get All current History
		iConfiguration.setHistoryUrl(iCurrentConfiguration.getHistoryUrl());

		// Update Authentication server
		iConfiguration.setAuthentication(new Authentication());
		if ((this.authenticationServer.getText() != null) && !this.authenticationServer.getText().endsWith("/")) {
			this.authenticationServer.setText(this.authenticationServer.getText() + "/");
		}
		iConfiguration.getAuthentication().setServer(this.authenticationServer.getText());

		// Update Console
		iConfiguration.setConsole(new Configuration.Console());
		iConfiguration.getConsole().setMaxLines(Integer.parseInt(this.consoleMaxLines.getText()));

		// Update Console.System
		iConfiguration.getConsole().setSystem(new Configuration.Console.System());
		iConfiguration.getConsole().getSystem().setMaxLines(Integer.parseInt(this.consoleSystemMaxLines.getText()));
		iConfiguration.getConsole().getSystem().setEnableOnStartup(this.consoleSystemEnable.isSelected());

		// Save
		return this.view.getController().save(iConfiguration);
	}

	public void writeXML(final Object aElement, final OutputStream aOutputStream, final String aPackage, final ClassLoader aClassLoader) {
		try {
			final JAXBContext iJaxbContext = JAXBContext.newInstance(aPackage, aClassLoader);
			final Marshaller iMarshaller = iJaxbContext.createMarshaller();
			iMarshaller.marshal(aElement, aOutputStream);
		} catch (final JAXBException aException) {
			aException.printStackTrace();
		}
	}
}
