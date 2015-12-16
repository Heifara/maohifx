/**
 *
 */
package com.maohi.software.maohifx.control;

import java.util.Comparator;

import org.controlsfx.control.textfield.AutoCompletionBinding.ISuggestionRequest;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/**
 * @author heifara
 *
 */
public class StringSuggestionProvider<T> extends SuggestionProvider<T>implements Comparator<T>, ListChangeListener<T> {

	private final StringConverter<T> stringConverter;
	private final ObservableList<T> suggestion;

	public StringSuggestionProvider(final ObservableList<T> aSuggestions, final StringConverter<T> aStringConverter) {
		this.stringConverter = aStringConverter;
		this.suggestion = aSuggestions;
		this.suggestion.addListener(this);

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

	@Override
	public void onChanged(final javafx.collections.ListChangeListener.Change<? extends T> aChange) {
		if (aChange.next()) {
			this.clearSuggestions();

			this.addPossibleSuggestions(this.suggestion);
		}
	}
}