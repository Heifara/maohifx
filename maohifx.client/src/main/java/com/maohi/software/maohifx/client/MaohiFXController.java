/**
 *
 */
package com.maohi.software.maohifx.client;

import java.net.URL;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response.Status;

import org.controlsfx.dialog.Dialogs;

import com.maohi.software.maohifx.client.event.ConnectEvent;
import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.event.SuccesEvent;
import com.maohi.software.maohifx.client.jaxb2.Configuration;
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

	private Profile profile;

	public MaohiFXController() {
		this.eventDispatcher = new EventHandlerManager(this);
	}

	public <T extends Event> void addEventHandler(final EventType<T> aEventType, final EventHandler<? super T> aEventHandler) {
		this.eventDispatcher.addEventFilter(aEventType, aEventHandler);
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

			final Profile iProfile = new Profile(aUsername, aPassword);

			final URLHandler iHandler = new URLHandler();
			iHandler.setOnSucces(new EventHandler<SuccesEvent>() {

				@Override
				public void handle(final SuccesEvent event) {
					MaohiFXController.this.profile = iProfile;
					MaohiFXController.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_SUCCES, this), new NotImplementedEventDispatchChain());
				}
			});
			iHandler.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

				@Override
				public void handle(final ExceptionEvent aEvent) {
					MaohiFXController.this.profile = null;

					final Status iStatus = Status.fromStatusCode(aEvent.getStatusCode());
					if (iStatus != null) {
						switch (iStatus) {
						case NOT_ACCEPTABLE:
							Dialogs.create().title("Erreur d'authentification").message("Le nom d'utilisateur ou le mot de passe sont incorrecte").showError();
							break;

						default:
							MaohiFXController.this.getView().displayException(aEvent.getException());
							MaohiFXController.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
							break;
						}
					} else {
						MaohiFXController.this.getView().displayException(aEvent.getException());
						MaohiFXController.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
					}
				}
			});
			iHandler.process(new URL(this.getConfiguration().getAuthenticationServer() + "maohifx.server/webapi/authentication/connect"), "post", Entity.json(iProfile));
			return true;
		} catch (final Exception aException) {
			this.getView().displayException(aException);
			this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
			return false;
		}
	}

	public void disconnect() {
		try {
			final URLHandler iHandler = new URLHandler();
			iHandler.setOnSucces(new EventHandler<SuccesEvent>() {

				@Override
				public void handle(final SuccesEvent event) {
					MaohiFXController.this.profile = null;
					MaohiFXController.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_SUCCES, this), new NotImplementedEventDispatchChain());
				}
			});
			iHandler.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

				@Override
				public void handle(final ExceptionEvent aEvent) {
					MaohiFXController.this.getView().displayException(aEvent.getException());
					MaohiFXController.this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
				}
			});
			iHandler.process(new URL(this.getConfiguration().getAuthenticationServer() + "maohifx.server/webapi/authentication/disconnect"), "post", Entity.json(this.profile));
		} catch (final Exception aException) {
			this.getView().displayException(aException);
			this.eventDispatcher.dispatchEvent(new ConnectEvent(ConnectEvent.CONNECT_ERROR, this), new NotImplementedEventDispatchChain());
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
		return this.profile;
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
		this.getView().show();
	}

	public ObjectProperty<MaohiFXView> viewProperty() {
		if (this.viewProperty == null) {
			this.viewProperty = new SimpleObjectProperty<MaohiFXView>();
		}
		return this.viewProperty;
	}

}
