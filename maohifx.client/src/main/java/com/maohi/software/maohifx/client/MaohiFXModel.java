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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.controlsfx.dialog.Dialogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.client.event.ConnectEvent;
import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.event.SuccesEvent;
import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.jaxb2.Configuration.Authentication;
import com.maohi.software.maohifx.client.jaxb2.Configuration.HistoryUrl;
import com.maohi.software.maohifx.client.jaxb2.Configuration.Home;
import com.maohi.software.maohifx.common.JaxbUtils;
import com.maohi.software.maohifx.common.Profile;
import com.sun.javafx.event.EventHandlerManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class MaohiFXModel {

	/**
	 * I dont know whats this is used for but still working without any implementation
	 *
	 * @author heifara
	 *
	 */
	public class NotImplementedEventDispatchChain implements EventDispatchChain {

		@Override
		public EventDispatchChain append(final EventDispatcher eventDispatcher) {
			return null;
		}

		@Override
		public Event dispatchEvent(final Event event) {
			return null;
		}

		@Override
		public EventDispatchChain prepend(final EventDispatcher eventDispatcher) {
			return null;
		}

	}

	private StringProperty configFilePathProperty;
	private EventHandler<ExceptionEvent> onExceptionThrown;

	private final EventHandlerManager eventDispatcher;

	private Profile profile;

	public MaohiFXModel(final String aConfigFilePath) {
		this.configFilePathProperty().set(aConfigFilePath);

		this.eventDispatcher = new EventHandlerManager(this);

		final File iFile = new File(this.configFilePathProperty().get());
		if (!iFile.exists()) {
			final Configuration iConfiguration = new Configuration();
			iConfiguration.setHome(new Home());
			iConfiguration.setHistoryUrl(new HistoryUrl());
			iConfiguration.getHome().setUrl("");
			iConfiguration.getHome().setAutoLoad(false);
			iConfiguration.setAuthentication(new Authentication());
			this.save(iConfiguration);
		}
	}

	public <T extends Event> void addEventHandler(final EventType<T> aEventType, final EventHandler<? super T> aEventHandler) {
		this.eventDispatcher.addEventFilter(aEventType, aEventHandler);
	}

	public StringProperty configFilePathProperty() {
		if (this.configFilePathProperty == null) {
			this.configFilePathProperty = new SimpleStringProperty("");
		}
		return this.configFilePathProperty;
	}

	public void connect(final Profile aProfile) {
		try {
			final URLHandler iHandler = new URLHandler();
			iHandler.setOnSucces(new EventHandler<SuccesEvent>() {

				@SuppressWarnings("unchecked")
				@Override
				public void handle(final SuccesEvent aEvent) {
					try {
						final Map<String, String> iMap = (Map<String, String>) aEvent.getItem();

						final Map<String, String> iParsedMap = new HashMap<>();
						for (final String iKey : iMap.keySet()) {
							iParsedMap.put('"' + iKey + '"', '"' + iMap.get(iKey) + '"');
						}

						MaohiFXModel.this.profile = new ObjectMapper().readValue(iParsedMap.toString().replace("=", ":"), Profile.class);
						MaohiFXModel.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_SUCCES, this), new NotImplementedEventDispatchChain());
					} catch (final Exception aException) {
						MaohiFXModel.this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
					}
				}
			});
			iHandler.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

				@Override
				public void handle(final ExceptionEvent aEvent) {
					MaohiFXModel.this.profile = null;

					final Status iStatus = Status.fromStatusCode(aEvent.getStatusCode());
					if (iStatus != null) {
						switch (iStatus) {
						case NOT_ACCEPTABLE:
							Dialogs.create().title("Erreur d'authentification").message("Le nom d'utilisateur ou le mot de passe sont incorrecte").showError();
							break;

						default:
							MaohiFXModel.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
							break;
						}
					} else {
						MaohiFXModel.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
					}
				}
			});

			final Thread iThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						iHandler.process(new URL(MaohiFXModel.this.getConfiguration().getAuthentication().getServer() + "maohifx.server/webapi/authentication/connect"), "post", Entity.json(aProfile));
					} catch (IOException | InterruptedException aException) {
						MaohiFXModel.this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
					}
				}
			});
			iThread.setName("Connection");
			iThread.start();
		} catch (final Exception aException) {
			this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
		}
	}

	public void disconnect() {
		try {
			final URLHandler iHandler = new URLHandler();
			iHandler.setOnSucces(new EventHandler<SuccesEvent>() {

				@Override
				public void handle(final SuccesEvent event) {
					MaohiFXModel.this.profile = null;
					MaohiFXModel.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_SUCCES, this), new NotImplementedEventDispatchChain());
				}
			});
			iHandler.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

				@Override
				public void handle(final ExceptionEvent aEvent) {
					MaohiFXModel.this.onExceptionThrown.handle(new ExceptionEvent(this, aEvent.getException(), -1));
					MaohiFXModel.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
				}
			});
			iHandler.process(new URL(this.getConfiguration().getAuthentication().getServer() + "maohifx.server/webapi/authentication/disconnect"), "post", Entity.json(this.profile));
		} catch (final Exception aException) {
			MaohiFXModel.this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
			this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
		}
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
			this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
		}

		return iConfiguration;
	}

	public EventHandler<ExceptionEvent> getOnExceptionThrown() {
		return this.onExceptionThrown;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public boolean save(final Configuration aConfiguration) {
		try {
			final File iConfigFile = new File(this.configFilePathProperty().get());
			final OutputStream iOutputStream = new FileOutputStream(iConfigFile);
			JaxbUtils.writeXML(aConfiguration, iOutputStream, "com.maohi.software.maohifx.client.jaxb2", this.getClass().getClassLoader());
			return true;
		} catch (final FileNotFoundException | JAXBException aException) {
			this.onExceptionThrown.handle(new ExceptionEvent(this, aException, -1));
			return false;
		}
	}

	public void setOnExceptionThrown(final EventHandler<ExceptionEvent> onExceptionThrown) {
		this.onExceptionThrown = onExceptionThrown;
	}

}
