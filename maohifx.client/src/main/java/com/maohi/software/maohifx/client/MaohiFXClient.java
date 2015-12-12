/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.maohi.software.maohifx.client.extendedtab.ExtendedTab;
import com.maohi.software.maohifx.control.Link;
import com.maohi.software.maohifx.control.Link.LinkTarget;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.application.Application;
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

/**
 * @author heifara
 *
 */
public class MaohiFXClient extends Application implements ListChangeListener<Tab>, ChangeListener<Tab>, LinkTarget {

	private final FXMLLoader loader;
	private Stage stage;
	private Scene scene;
	private BorderPane mainPane;
	private TabPane tabpane;
	private ExtendedTab currentTab;

	public MaohiFXClient() {
		this.loader = new FXMLLoader();
		this.loader.setBuilderFactory(new MaohiFXBuilderFactory());
		this.loader.setLocation(this.getClass().getResource("MaohiFXClient.fxml"));
	}

	@Override
	public void changed(final ObservableValue<? extends Tab> aObservable, final Tab aOldValue, final Tab aNewValue) {
		this.currentTab = (ExtendedTab) aNewValue;
	}

	@Override
	public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
		JOptionPane.showMessageDialog(null, aException.getMessage());
	}

	@Override
	public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final String aRecipeeId) {
		switch (aLink.getTarget()) {
		case BLANK:
			this.tabpane.getTabs().add(new ExtendedTab(this.loader, aLink.getHref()));
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

	@Override
	public void start(final Stage aStage) {
		try {
			this.mainPane = this.loader.load();

			this.stage = aStage;
			this.stage.getIcons().add(new Image("app.png"));
			this.scene = new Scene(this.mainPane, 800, 600);
			this.scene.getStylesheets().add("MaohiFXClient.css");

			this.tabpane = (TabPane) this.mainPane.getCenter();
			this.tabpane.getSelectionModel().selectedItemProperty().addListener(this);
			this.tabpane.getTabs().add(new ExtendedTab(this.loader));
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
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}
