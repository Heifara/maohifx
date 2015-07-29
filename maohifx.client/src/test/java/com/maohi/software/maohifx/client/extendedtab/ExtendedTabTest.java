/**
 * 
 */
package com.maohi.software.maohifx.client.extendedtab;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * @author heifara
 *
 */
public class ExtendedTabTest extends Application {

	public static void main(String[] aArgs) {
		launch(ExtendedTabTest.class);
	}

	@Override
	public void start(Stage aStage) throws Exception {
		TabPane iTabPane = new TabPane();
		iTabPane.getTabs().addListener(new ListChangeListener<Tab>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Tab> c) {
				c.next();
				if (c.wasRemoved()) {
					System.out.println("removed");
					if (iTabPane.getTabs().size() == 0) {
						aStage.close();
					}
				} else if (c.wasAdded()) {
					System.out.println("added");
				}
			}
		});
		iTabPane.getTabs().add(new ExtendedTab());

		aStage.setScene(new Scene(iTabPane));
		aStage.setTitle("ExtendedTab");
		aStage.setWidth(600);
		aStage.setHeight(400);
		aStage.show();
	}

}