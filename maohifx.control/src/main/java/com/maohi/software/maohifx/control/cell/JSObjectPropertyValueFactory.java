/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import java.util.Map;

import com.sun.javafx.event.EventHandlerManager;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import netscape.javascript.JSObject;

/**
 * @author heifara
 *
 */
@SuppressWarnings({ "restriction", "unchecked" })
public class JSObjectPropertyValueFactory<T> extends PropertyValueFactory<JSObject, T>implements EventHandler<CellEditEvent<JSObject, T>> {

	private final EventHandlerManager eventHandlerManager;

	private ObjectProperty<EventHandler<CellEditEvent<JSObject, T>>> onEditCommit;
	private boolean added;

	public JSObjectPropertyValueFactory(@NamedArg("property") final String property) {
		super(property);

		this.added = false;
		this.eventHandlerManager = new EventHandlerManager(this);
	}

	@Override
	public ObservableValue<T> call(final CellDataFeatures<JSObject, T> aCellDataFeatures) {
		final TableColumn<JSObject, T> iTableColumn = aCellDataFeatures.getTableColumn();

		if (!this.added) {
			iTableColumn.addEventHandler(TableColumn.editCommitEvent(), this);
			this.added = true;
		}

		final ObservableValue<T> iObservableValue = this.getObservableValue(iTableColumn, aCellDataFeatures);
		return iObservableValue;
	}

	private ObservableValue<T> getObservableValue(final TableColumn<JSObject, T> aTableColumn, final CellDataFeatures<JSObject, T> aCellDataFeatures) {
		if (aCellDataFeatures.getValue() instanceof Map) {
			final Map<?, ?> iData = (Map<?, ?>) aCellDataFeatures.getValue();
			final Object iPropertyValue = iData.get(this.getProperty());
			if (iPropertyValue instanceof ScriptObjectMirror) {
				final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) iPropertyValue;
				return new ReadOnlyObjectWrapper<T>((T) iScriptObject);
			} else {
				return (ObservableValue<T>) iData.get(this.getProperty());
			}
		} else {
			final JSObject iData = aCellDataFeatures.getValue();
			return new ReadOnlyObjectWrapper<T>(iData.getMember(this.getProperty()) != null ? (T) iData.getMember(this.getProperty()) : null);
		}
	}

	public final EventHandler<CellEditEvent<JSObject, T>> getOnEditCommit() {
		return this.onEditCommit == null ? null : this.onEditCommit.get();
	}

	@Override
	public void handle(final CellEditEvent<JSObject, T> aEvent) {
		if ((aEvent instanceof CellEditEvent) && (this.getOnEditCommit() != null)) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					final EventHandler<CellEditEvent<JSObject, T>> iOnEditCommit = JSObjectPropertyValueFactory.this.getOnEditCommit();
					iOnEditCommit.handle(aEvent);
				}
			});
		}
	}

	public ObjectProperty<EventHandler<CellEditEvent<JSObject, T>>> onEditCommitProperty() {
		if (this.onEditCommit == null) {
			this.onEditCommit = new SimpleObjectProperty<EventHandler<CellEditEvent<JSObject, T>>>(this, "onEditCommit") {
				@Override
				protected void invalidated() {
					JSObjectPropertyValueFactory.this.eventHandlerManager.setEventHandler(TableColumn.<JSObject, T> editCommitEvent(), this.get());
				}
			};
		}
		return this.onEditCommit;
	}

	public final void setOnEditCommit(final EventHandler<CellEditEvent<JSObject, T>> aValue) {
		this.onEditCommitProperty().set(aValue);
	}

}
