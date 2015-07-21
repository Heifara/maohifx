/**
 * 
 */
package com.maohi.software.samples.tableview.json;

import javafx.beans.NamedArg;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author heifara
 *
 */
public class JSONValueFactory<T> extends PropertyValueFactory<JSONItem, T> {

	public JSONValueFactory(@NamedArg("property") String property) {
		super(property);
	}

	@Override
	public ObservableValue<T> call(CellDataFeatures<JSONItem, T> aCellDataFeatures) {
		JSONItem iData = aCellDataFeatures.getValue();
		return new ReadOnlyObjectWrapper<T>(iData.getValue(this.getProperty()));
	}

}
