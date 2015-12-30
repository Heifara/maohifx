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

	private final MaohiFXView view;
	private final FXMLLoader loader;

	@FXML
	private TextField homeUrl;

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
		this.homeUrl.setText(iConfiguration.getHomeUrl());
	}

	@FXML
	public void okEvent() {
		if (this.save()) {
			this.cancelEvent();
		}
	}

	private boolean save() {
		final Configuration iConfiguration = new Configuration();
		iConfiguration.setHomeUrl(this.homeUrl.getText());

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
