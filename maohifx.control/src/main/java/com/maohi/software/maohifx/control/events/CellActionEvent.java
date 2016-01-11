/**
 *
 */
package com.maohi.software.maohifx.control.events;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

/**
 * @author heifara
 *
 */
public class CellActionEvent<S, T> extends ActionEvent {

	private static final long serialVersionUID = 1L;
	private final TableRow<T> tableRow;
	private final TableColumn<S, T> tableColumn;
	private final Object item;
	private final int index;

	public CellActionEvent(final Object aSource, final ActionEvent aEvent, final TableRow<T> aTableRow, final TableColumn<S, T> aTableColumn, final Object aItem, final int aIndex) {
		super(aSource, aEvent != null ? aEvent.getTarget() : null);

		this.tableRow = aTableRow;
		this.tableColumn = aTableColumn;
		this.item = aItem;
		this.index = aIndex;
	}

	public int getIndex() {
		return this.index;
	}

	public Object getItem() {
		return this.item;
	}

	public TableColumn<S, T> getTableColumn() {
		return this.tableColumn;
	}

	public TableRow<T> getTableRow() {
		return this.tableRow;
	}

}
