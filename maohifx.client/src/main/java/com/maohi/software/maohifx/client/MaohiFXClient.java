/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.maohi.software.maohifx.client.extendedtab.ExtendedTab;
import com.maohi.software.maohifx.control.Link;
import com.maohi.software.maohifx.control.LinkTarget;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class MaohiFXClient extends Application implements ListChangeListener<Tab>, LinkTarget {

	private final ExtFXMLLoader loader;
	private Stage stage;
	private Scene scene;
	private BorderPane mainPane;
	private TabPane tabpane;

	public MaohiFXClient() {
		this.loader = new ExtFXMLLoader();
		this.loader.setLocation(this.getClass().getResource("MaohiFXClient.fxml"));
	}

	@Override
	public void error(final Link link, final ActionEvent aEvent, final Throwable aException) {
		JOptionPane.showMessageDialog(null, aException.getMessage());
	}

	@Override
	public void handle(final Link aLink, final ActionEvent aEvent, final FXMLLoader aLoader, final Node aNode) {
		aLoader.getNamespace().put("$tab", this.tabpane.getSelectionModel().getSelectedItem());
		aLoader.getNamespace().put("$tabpane", this.tabpane);
		this.tabpane.getTabs().add(new ExtendedTab(this.loader, aLink.getHref()));
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
			this.scene = new Scene(this.mainPane);
			this.scene.getStylesheets().add("MaohiFXClient.css");

			this.tabpane = (TabPane) this.mainPane.getCenter();
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
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}
