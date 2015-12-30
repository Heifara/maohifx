/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.jaxb2.Configuration.HistoryUrl;
import com.maohi.software.maohifx.common.JaxbUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;

/**
 * @author heifara
 *
 */
public class MaohiFXModel {

	private StringProperty configFilePathProperty;
	private EventHandler<ExceptionEvent> onExceptionThrown;

	public MaohiFXModel(final String aConfigFilePath) {
		this.configFilePathProperty().set(aConfigFilePath);
	}

	public StringProperty configFilePathProperty() {
		if (this.configFilePathProperty == null) {
			this.configFilePathProperty = new SimpleStringProperty("");
		}
		return this.configFilePathProperty;
	}

	public Configuration getConfiguration() {
		Configuration iConfiguration = null;
		try {
			final InputStream iInputStream = new FileInputStream(this.configFilePathProperty().get());
			if (iInputStream != null) {
				iConfiguration = (Configuration) JaxbUtils.readXML(iInputStream, "com.maohi.software.maohifx.client.jaxb2", this.getClass().getClassLoader());
				if (iConfiguration.getHistoryUrl() == null) {
					iConfiguration.setHistoryUrl(new HistoryUrl());
				}
			}
		} catch (final FileNotFoundException | JAXBException aException) {
			this.onExceptionThrown.handle(new ExceptionEvent(this, aException));
		}

		return iConfiguration;
	}

	public EventHandler<ExceptionEvent> getOnExceptionThrown() {
		return this.onExceptionThrown;
	}

	public boolean save(final Configuration aConfiguration) {
		try {
			final File iConfigFile = new File(this.configFilePathProperty().get());
			final OutputStream iOutputStream = new FileOutputStream(iConfigFile);
			JaxbUtils.writeXML(aConfiguration, iOutputStream, "com.maohi.software.maohifx.client.jaxb2", this.getClass().getClassLoader());
			return true;
		} catch (final FileNotFoundException | JAXBException aException) {
			this.onExceptionThrown.handle(new ExceptionEvent(this, aException));
			return false;
		}
	}

	public void setOnExceptionThrown(final EventHandler<ExceptionEvent> onExceptionThrown) {
		this.onExceptionThrown = onExceptionThrown;
	}

}
