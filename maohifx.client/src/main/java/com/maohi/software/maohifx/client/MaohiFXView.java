/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.util.List;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.maohi.software.maohifx.client.event.ExceptionEvent;
import com.maohi.software.maohifx.client.jaxb2.Configuration;
import com.maohi.software.maohifx.control.Link;
import com.maohi.software.maohifx.control.Link.LinkTarget;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

/**
 * @author heifara
 *
 */
@SuppressWarnings("deprecation")
public class MaohiFXView extends BorderPane implements BuilderFactory, Callback<Class<?>, Object>, ListChangeListener<Tab>, ChangeListener<Tab>, LinkTarget {

	private final MaohiFXController controller;
	private final Stage stage;
	private final Scene scene;
	private final BuilderFactory baseFactory;
	private final FXMLLoader loader;

	private ChangeListener<MaohiFXModel> onModelChanged;
	private ExtendedTab currentTab;

	@FXML
	private TabPane tabPane;

	public MaohiFXView(final MaohiFXController aController, final Stage aStage) {
		this.controller = aController;
		this.stage = aStage;

		this.scene = new Scene(this, 800, 600);
		this.baseFactory = new JavaFXBuilderFactory();

		try {
			this.loader = new FXMLLoader();
			this.loader.setBuilderFactory(this);
			this.loader.setControllerFactory(this);
			this.loader.setLocation(this.getClass().getResource("MaohiFXView.fxml"));
			this.loader.setRoot(this);
			this.loader.setController(this);

			this.loader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}

		this.init();
	}

	@Override
	public Object call(final Class<?> aParam) {
		return null;
	}

	@Override
	public void changed(final ObservableValue<? extends Tab> aObservable, final Tab aOldValue, final Tab aNewValue) {
		this.currentTab = (ExtendedTab) aNewValue;
	}

	public void closeApplication() {
		final Action response = Dialogs.create().owner(this.stage).title("Fermer l'application ?").masthead("L'application est sur le point de se fermer").message("Voulez vous vraiment fermer l'application ?").actions(Dialog.ACTION_YES, Dialog.ACTION_NO).showConfirm();

		if (response == Dialog.ACTION_YES) {
			this.stage.close();
		} else if (response == Dialog.ACTION_NO) {
			if (this.tabPane.getTabs().size() == 0) {
				this.newTab();
			}
		}
	}

	public void closeCurrentTab() {
		this.tabPane.getTabs().remove(this.currentTab);
	}

	public void displayException(final Throwable aException) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Dialogs.create().owner(MaohiFXView.this.stage).title(aException.getClass().getSimpleName()).masthead("Une erreur inattendue est survenue").message(aException.getMessage()).showException(aException);
			}
		});
	}

	@Override
	public void error(final Link aLink, final ActionEvent aEvent, final Throwable aException) {
		this.displayException(aException);
	}

	@Override
	public Builder<?> getBuilder(final Class<?> aType) {
		return this.baseFactory.getBuilder(aType);
	}

	public BuilderFactory getBuilderFactory() {
		return this;
	}

	public MaohiFXController getController() {
		return this.controller;
	}

	public ChangeListener<MaohiFXModel> getOnModelChanged() {
		if (this.onModelChanged == null) {
			this.onModelChanged = new ChangeListener<MaohiFXModel>() {

				@Override
				public void changed(final ObservableValue<? extends MaohiFXModel> aObservable, final MaohiFXModel aOldValue, final MaohiFXModel aNewValue) {
					if (aOldValue != null) {
						aOldValue.setOnExceptionThrown(null);
					}

					aNewValue.setOnExceptionThrown(new EventHandler<ExceptionEvent>() {

						@Override
						public void handle(final ExceptionEvent aEvent) {
							MaohiFXView.this.displayException(aEvent.getException());
						}

					});
				}
			};
		}
		return this.onModelChanged;
	}

	@Override
	public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final String aRecipeId) {
		switch (aLink.getTarget()) {
		case BLANK:
			final ExtendedTab iTab = new ExtendedTab(this);
			iTab.setUrl(aLink.getHref());
			iTab.refreshTab(aLink.getText());

			this.tabPane.getTabs().add(iTab);
			break;

		case SELF:
			this.currentTab.refreshTab(aLoader.getLocation().toExternalForm(), aLink.getText());
			break;

		case FRAMENAME:
			this.currentTab.refreshTab(aLink.getHref(), (Parent) this.currentTab.getContent().lookup(aRecipeId), aLink.getText());
			break;

		default:
			break;
		}
	}

	private void init() {
		this.controller.modelProperty().addListener(this.getOnModelChanged());

		this.stage.getIcons().add(new Image("app.png"));
		this.stage.setScene(this.scene);

		this.scene.getStylesheets().add("MaohiFXClient.css");

		this.tabPane.getSelectionModel().selectedItemProperty().addListener(this);
		this.tabPane.getTabs().addListener(this);

		Link.setHrefTarget(HrefTarget.BLANK, this);
		Link.setHrefTarget(HrefTarget.SELF, this);
		Link.setHrefTarget(HrefTarget.FRAMENAME, this);
	}

	public void newTab() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				final ExtendedTab iTab = new ExtendedTab(MaohiFXView.this);
				iTab.setText("Nouvelle Onglet");

				final Configuration iConfiguration = MaohiFXView.this.controller.getConfiguration();
				if (iConfiguration.getHome().isAutoLoad()) {
					iTab.homeEvent(new ActionEvent());
				}

				MaohiFXView.this.tabPane.getTabs().add(iTab);
			}
		});
	}

	@Override
	public void onChanged(final javafx.collections.ListChangeListener.Change<? extends Tab> aChange) {
		if (aChange.next()) {
			if (aChange.wasRemoved()) {
				if (this.tabPane.getTabs().size() == 0) {
					this.closeApplication();
				}
			} else if (aChange.wasAdded()) {
				final List<? extends Tab> iSelectedItems = aChange.getAddedSubList();
				final Tab iTab = iSelectedItems.get(0);
				this.tabPane.getSelectionModel().select(iTab);
			} else if (aChange.wasReplaced()) {
			} else if (aChange.wasUpdated()) {
			} else if (aChange.wasPermutated()) {
			}
		}
	}

	public void populateNamespace(final FXMLLoader aLoader) {
		aLoader.getNamespace().put("$stage", this.stage);
		aLoader.getNamespace().put("$tabpane", this.tabPane);
		aLoader.getNamespace().put("$scene", this.scene);
		aLoader.getNamespace().put("$profile", this.controller.getProfile());
	}

	public void show() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				MaohiFXView.this.stage.show();
				MaohiFXView.this.newTab();
			}
		});
	}
}
