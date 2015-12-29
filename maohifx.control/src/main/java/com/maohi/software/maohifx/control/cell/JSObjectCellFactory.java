/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;

import com.maohi.software.maohifx.common.ExtStringConverter;
import com.sun.javafx.event.EventHandlerManager;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author heifara
 *
 */
@SuppressWarnings({ "rawtypes" })
public class JSObjectCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	private final EventHandlerManager eventHandlerManager;

	private ObservableList<T> autoCompletion;
	private ObjectProperty<EventHandler<AutoCompletionEvent>> onAutoCompletion;
	private ObjectProperty<StringConverter<T>> stringConverter;

	public JSObjectCellFactory() {
		this.eventHandlerManager = new EventHandlerManager(this);
	}

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> aParam) {
		final TextFieldTableCell<S, T> iTableCell = new TextFieldTableCell<S, T>(this.getStringConverter());
		if (this.getAutoCompletion() != null) {
			iTableCell.getTextfield().setAutoCompletion(this.getAutoCompletion());
			if (this.getOnAutoCompletion() != null) {
				iTableCell.getTextfield().setOnAutoCompletion(this.getOnAutoCompletion());
			}
		}
		return iTableCell;
	}

	public ObservableList<T> getAutoCompletion() {
		return this.autoCompletion;
	}

	public EventHandler<AutoCompletionEvent> getOnAutoCompletion() {
		return this.onAutoCompletionProperty().get();
	}

	public StringConverter<T> getStringConverter() {
		return this.stringConverterProperty().get();
	}

	public ObjectProperty<EventHandler<AutoCompletionEvent>> onAutoCompletionProperty() {
		if (this.onAutoCompletion == null) {
			this.onAutoCompletion = new SimpleObjectProperty<EventHandler<AutoCompletionEvent>>(this, "onAutoCompletion") {
				@Override
				protected void invalidated() {
					JSObjectCellFactory.this.eventHandlerManager.setEventHandler(AutoCompletionEvent.AUTO_COMPLETED, this.get());
				}
			};
		}
		return this.onAutoCompletion;
	}

	public void setAutoCompletion(final ObservableList<T> autoCompletion) {
		this.autoCompletion = autoCompletion;
	}

	public void setOnAutoCompletion(final EventHandler<AutoCompletionEvent> onAutoCompletion) {
		this.onAutoCompletionProperty().set(onAutoCompletion);
	}

	protected ObjectProperty<StringConverter<T>> stringConverterProperty() {
		if (this.stringConverter == null) {
			this.stringConverter = new SimpleObjectProperty<StringConverter<T>>(new ExtStringConverter<>());
		}
		return this.stringConverter;
	}

}
