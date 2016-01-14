/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import com.sun.javafx.event.EventHandlerManager;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.Undefined;
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

		final ObservableValue<T> iObservableValue = this.getObservableValue(aCellDataFeatures.getValue());
		return iObservableValue;
	}

	private ObservableValue<T> getObservableValue(final Object aValue) {
		if (aValue == null) {
			return null;
		} else if (aValue instanceof ScriptObjectMirror) {
			final ScriptObjectMirror iScriptObject = (ScriptObjectMirror) aValue;
			final Object iPropertyValue = iScriptObject.getMember(this.getProperty());
			return this.getObservableValue(iPropertyValue);
		} else if (aValue instanceof ObservableValue) {
			final ObservableValue<T> iObservableValue = (ObservableValue<T>) aValue;
			return iObservableValue;
		} else if (aValue instanceof Undefined) {
			return (ObservableValue<T>) new SimpleStringProperty("");
		} else {
			return new SimpleObjectProperty<T>((T) aValue);
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
