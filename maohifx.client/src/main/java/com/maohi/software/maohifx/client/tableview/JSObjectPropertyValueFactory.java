/**
 * 
 */
package com.maohi.software.maohifx.client.tableview;

import java.util.Map;

import javafx.beans.NamedArg;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.api.scripting.JSObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings({ "restriction", "unchecked" })
public class JSObjectPropertyValueFactory<T> extends PropertyValueFactory<JSObject, T> {

	public JSObjectPropertyValueFactory(@NamedArg("property") String property) {
		super(property);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ObservableValue<T> call(CellDataFeatures<JSObject, T> aCellDataFeatures) {
		if (aCellDataFeatures.getValue() instanceof Map) {
			Map iData = (Map) aCellDataFeatures.getValue();
			return (ObservableValue<T>) iData.get(getProperty());
		} else {
			JSObject iData = aCellDataFeatures.getValue();
			return new ReadOnlyObjectWrapper<T>(iData.hasMember(getProperty()) ? (T) iData.getMember(getProperty()) : null);
		}
	}

}
