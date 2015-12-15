/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import java.util.Collection;
import java.util.Comparator;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding.ISuggestionRequest;

import com.sun.javafx.event.EventHandlerManager;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JSObjectCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	/**
	 * @author heifara
	 *
	 */
	private class StringSuggestionProvider extends SuggestionProvider<T>implements Comparator<T> {

		private final StringConverter<T> stringConverter;

		public StringSuggestionProvider(final Collection<T> aSuggestions, final StringConverter<T> aStringConverter) {
			this.stringConverter = aStringConverter;

			this.addPossibleSuggestions(aSuggestions);
		}

		@Override
		public int compare(final T aObject1, final T aObject2) {
			final String iString1 = StringSuggestionProvider.this.stringConverter.toString(aObject1);
			final String iString2 = StringSuggestionProvider.this.stringConverter.toString(aObject2);
			return iString1.compareTo(iString2);
		}

		@Override
		protected Comparator<T> getComparator() {
			return this;
		}

		@Override
		protected boolean isMatch(final T aSuggestion, final ISuggestionRequest aRequest) {
			final String iUserTextLower = aRequest.getUserText().toLowerCase();
			final String iSuggestionStr = this.stringConverter.toString(aSuggestion).toLowerCase();
			return iSuggestionStr.contains(iUserTextLower) && !iSuggestionStr.equals(iUserTextLower);
		}
	}

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
		if (this.autoCompletion != null) {
			final AutoCompletionBinding<T> iAutoCompletionBinding = new AutoCompletionTextFieldBinding(iTableCell.getTextfield(), new StringSuggestionProvider(this.autoCompletion, this.getStringConverter()), this.getStringConverter());
			if (this.onAutoCompletion != null) {
				iAutoCompletionBinding.addEventHandler(AutoCompletionEvent.AUTO_COMPLETED, this.getOnAutoCompletion());
			}
		}
		return iTableCell;
	}

	public ObservableList<T> getAutoCompletion() {
		return this.autoCompletion;
	}

	public EventHandler<AutoCompletionEvent> getOnAutoCompletion() {
		return this.onAutoCompletion.get();
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
		this.onAutoCompletion.set(onAutoCompletion);
	}

	protected ObjectProperty<StringConverter<T>> stringConverterProperty() {
		if (this.stringConverter == null) {
			this.stringConverter = new SimpleObjectProperty<StringConverter<T>>();
		}
		return this.stringConverter;
	}

}
