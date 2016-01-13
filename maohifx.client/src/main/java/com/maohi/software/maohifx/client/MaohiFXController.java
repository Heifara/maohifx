/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.PrintStream;

import org.controlsfx.dialog.Dialogs;

import com.maohi.software.maohifx.client.event.ConnectEvent;
import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.client.jaxb2.Configuration.Authentication;
import com.maohi.software.maohifx.common.Profile;
import com.sun.javafx.event.EventHandlerManager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class MaohiFXController {

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

	private ObjectProperty<MaohiFXModel> modelProperty;
	private ObjectProperty<MaohiFXView> viewProperty;
	private ObjectProperty<Configuration> configurationProperty;

	private EventHandler<Event> onModelChanged;

	private final EventHandlerManager eventDispatcher;

	private ConsoleRedirectOutputStream systemOutputStream;

	public MaohiFXController() {
		this.eventDispatcher = new EventHandlerManager(this);
	}

	public <T extends Event> void addEventHandler(final EventType<T> aEventType, final EventHandler<? super T> aEventHandler) {
		this.eventDispatcher.addEventFilter(aEventType, aEventHandler);
	}

	private void autoConnect() {
		final Configuration iConfiguration = this.getConfiguration();
		if ((iConfiguration.getAuthentication() != null) && (iConfiguration.getAuthentication().getUsername() != null) && !iConfiguration.getAuthentication().getUsername().isEmpty()) {
			final Authentication iAuthentication = iConfiguration.getAuthentication();
			if (!this.connect(iAuthentication.getUsername(), iAuthentication.getPassword())) {
				this.getView().displayException(new Exception("Erreur lors de la connection au serveur"));
			}
		}
	}

	public ObjectProperty<Configuration> configurationProperty() {
		if (this.configurationProperty == null) {
			this.configurationProperty = new SimpleObjectProperty<Configuration>();
		}
		return this.configurationProperty;
	}

	public boolean connect(final String aUsername, final String aPassword) {
		try {
			if (aUsername.isEmpty()) {
				Dialogs.create().title("Erreur d'authentification").message("Le nom d'utilisateur ne peut pas être vide").showError();
				this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
				return false;
			}

			if (aPassword.isEmpty()) {
				Dialogs.create().title("Erreur d'authentification").message("Le mot de passe ne peut pas être vide").showError();
				this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
				return false;
			}

			this.getModel().connect(new Profile(aUsername, aPassword));
			return true;
		} catch (final Exception aException) {
			this.getView().displayException(aException);
			this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
			return false;
		}
	}

	public void disconnect() {
		try {
			final Configuration iConfiguration = this.getConfiguration();
			if ((iConfiguration.getAuthentication() != null) && (iConfiguration.getAuthentication().getUsername() != null) && !iConfiguration.getAuthentication().getUsername().isEmpty()) {
				final Authentication iAuthentication = iConfiguration.getAuthentication();
				iAuthentication.setUsername("");
				iAuthentication.setPassword("");
				this.getModel().save(iConfiguration);
			}

			this.getModel().disconnect();
		} catch (final Exception aException) {
			this.getView().displayException(aException);
			this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
		}
	}

	private void enableDisableSystemConsole() {
		final Configuration iConfiguration = this.getConfiguration();
		if ((iConfiguration.getConsole() != null) && (iConfiguration.getConsole().getSystem() != null) && iConfiguration.getConsole().getSystem().isEnableOnStartup()) {
			this.systemOutputStream = new ConsoleRedirectOutputStream();
			final PrintStream iPrintStream = new PrintStream(this.systemOutputStream);
			System.setOut(iPrintStream);
			System.setErr(iPrintStream);
		}
	}

	public Configuration getConfiguration() {
		return this.configurationProperty().get();
	}

	public MaohiFXModel getModel() {
		return this.modelProperty().get();
	}

	public EventHandler<Event> getOnModelChanged() {
		if (this.onModelChanged == null) {
			this.onModelChanged = new EventHandler<Event>() {

				@Override
				public void handle(final Event aEvent) {
					final Configuration iConfiguration = MaohiFXController.this.getModel().getConfiguration();
					MaohiFXController.this.configurationProperty().set(iConfiguration);
				}
			};
		}
		return this.onModelChanged;
	}

	public Profile getProfile() {
		return this.getModel().getProfile();
	}

	public ConsoleRedirectOutputStream getSystemOutputStream() {
		return this.systemOutputStream;
	}

	public MaohiFXView getView() {
		return this.viewProperty().get();
	}

	public boolean isConnected() {
		if (this.getProfile() == null) {
			return false;
		} else {
			return true;
		}
	}

	public ObjectProperty<MaohiFXModel> modelProperty() {
		if (this.modelProperty == null) {
			this.modelProperty = new SimpleObjectProperty<MaohiFXModel>();
			this.modelProperty.addListener(new ChangeListener<MaohiFXModel>() {

				@Override
				public void changed(final ObservableValue<? extends MaohiFXModel> aObservable, final MaohiFXModel aOldValue, final MaohiFXModel aNewValue) {
					MaohiFXController.this.getOnModelChanged().handle(new Event(Event.ANY));
				}
			});
		}
		return this.modelProperty;
	}

	public boolean save(final Configuration aConfiguration) {
		if (this.getModel().save(aConfiguration)) {
			this.configurationProperty().set(aConfiguration);
			return true;
		} else {
			return false;
		}
	}

	public void setModel(final MaohiFXModel aModel) {
		this.modelProperty().set(aModel);
	}

	public void setView(final MaohiFXView aView) {
		this.viewProperty().set(aView);
	}

	public void show() {
		this.enableDisableSystemConsole();

		this.autoConnect();

		this.getView().show();
	}

	public ObjectProperty<MaohiFXView> viewProperty() {
		if (this.viewProperty == null) {
			this.viewProperty = new SimpleObjectProperty<MaohiFXView>();
		}
		return this.viewProperty;
	}

}
