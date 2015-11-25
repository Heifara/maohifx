/**
 * 
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.util.List;

import com.maohi.software.maohifx.client.extendedtab.ExtendedTab;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author heifara
 *
 */
public class MaohiFXClient extends Application implements ListChangeListener<Tab> {

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
				List<? extends Tab> iSelectedItems = aChange.getAddedSubList();
				Tab iTab = iSelectedItems.get(0);
				tabpane.getSelectionModel().select(iTab);
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
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}
}
