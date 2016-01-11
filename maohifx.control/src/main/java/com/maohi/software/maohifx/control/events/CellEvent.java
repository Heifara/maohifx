/**
 *
 */
package com.maohi.software.maohifx.control.events;

import javafx.event.EventType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

/**
 * @author heifara
 *
 */
public class CellEvent<S, T> extends CellEditEvent<S, T> {

	private static final long serialVersionUID = 1L;

	public CellEvent(final Object aSource, final TableView<S> aTable, final TablePosition<S, T> aTablePosition, final EventType<CellEditEvent<S, T>> aEventType, final T aNewValue) {
		super(aTable, aTablePosition, aEventType, aNewValue);

		this.source = aSource;
	}

}
