/**
 *
 */
package com.maohi.software.maohifx.control;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.CustomTextField;

import com.maohi.software.maohifx.common.JSObjectStringConverter;
import com.sun.javafx.event.EventHandlerManager;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.util.StringConverter;

/**
 * @author heifara
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TextField extends CustomTextField {

	private final EventHandlerManager eventHandlerManager;

	private ObservableList autoCompletion;
	private ObjectProperty<EventHandler<AutoCompletionEvent>> onAutoCompletion;
	private ObjectProperty<StringConverter> stringConverter;
	private AutoCompletionBinding autoCompletionBinding;

	public TextField() {
		this.eventHandlerManager = new EventHandlerManager(this);
	}

	public ObservableList getAutoCompletion() {
		return this.autoCompletion;
	}

	public EventHandler<AutoCompletionEvent> getOnAutoCompletion() {
		return this.onAutoCompletionProperty().get();
	}

	public StringConverter getStringConverter() {
		return this.stringConverterProperty().get();
	}

	public ObjectProperty<EventHandler<AutoCompletionEvent>> onAutoCompletionProperty() {
		if (this.onAutoCompletion == null) {
			this.onAutoCompletion = new SimpleObjectProperty<EventHandler<AutoCompletionEvent>>(this, "onAutoCompletion") {
				@Override
				protected void invalidated() {
					TextField.this.eventHandlerManager.setEventHandler(AutoCompletionEvent.AUTO_COMPLETED, this.get());
				}
			};
		}
		return this.onAutoCompletion;
	}

	public void setAutoCompletion(final ObservableList autoCompletion) {
		this.autoCompletion = autoCompletion;
		this.autoCompletionBinding = new AutoCompletionTextFieldBinding(this, new StringSuggestionProvider(this.autoCompletion, this.getStringConverter()), this.getStringConverter());
	}

	public void setOnAutoCompletion(final EventHandler<AutoCompletionEvent> onAutoCompletion) {
		this.onAutoCompletionProperty().set(onAutoCompletion);
		if (this.autoCompletionBinding != null) {
			this.autoCompletionBinding.addEventHandler(AutoCompletionEvent.AUTO_COMPLETED, this.getOnAutoCompletion());
		}
	}

	protected ObjectProperty<StringConverter> stringConverterProperty() {
		if (this.stringConverter == null) {
			this.stringConverter = new SimpleObjectProperty<StringConverter>(new JSObjectStringConverter<>());
		}
		return this.stringConverter;
	}
}
