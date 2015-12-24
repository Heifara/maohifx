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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ConfigPane extends BorderPane implements Initializable {

	private final MaohiFXClient controller;
	private FXMLLoader loader;
	private final Stage dialog;

	@FXML
	private TextField homeUrl;

	public ConfigPane(final MaohiFXClient aController, final FXMLLoader aParent, final Stage aDialog) {
		this.controller = aController;
		this.dialog = aDialog;

		try {
			this.loader = new FXMLLoader();
			this.loader.setBuilderFactory(aParent.getBuilderFactory());
			this.loader.setLocation(this.getClass().getResource("ConfigPane.fxml"));
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

	@FXML
	public void applyEvent() {
		this.save();
	}

	@FXML
	public void cancelEvent() {
		this.dialog.close();
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		final Configuration iConfiguration = this.controller.getConfiguration();

		this.homeUrl.setText(iConfiguration.getHomeUrl());
	}

	@FXML
	public void okEvent() {
		if (this.save()) {
			this.dialog.close();
		}
	}

	private boolean save() {
		final Configuration iConfiguration = new Configuration();
		iConfiguration.setHomeUrl(this.homeUrl.getText());

		return this.controller.saveConfiguration(iConfiguration);
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
