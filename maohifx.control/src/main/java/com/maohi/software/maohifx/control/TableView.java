/**
 *
 */
package com.maohi.software.maohifx.control;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * @author heifara
 *
 */
public class TableView<S> extends javafx.scene.control.TableView<S>implements EventHandler<Event>, ListChangeListener<TableColumn<S, ?>> {

	public class FilterChangeListener implements ChangeListener<String> {

		private final TableView<S> tableView;

		public FilterChangeListener(final TableView<S> aTableView) {
			this.tableView = aTableView;
		}

		@Override
		public void changed(final ObservableValue<? extends String> aObservable, final String aOldValue, final String aNewValue) {
			this.tableView.select(aNewValue);
		}

	}

	private final StringProperty filter;
	private TablePosition<S, ?> nextPosition;
	private TablePosition<S, ?> previousPosition;

	public TableView() {
		this.filter = new SimpleStringProperty();
		this.filter.addListener(new FilterChangeListener(this));

		this.setEventHandler(Event.ANY, this);

		this.getColumns().addListener(this);

		try {
			final FXMLLoader iLoader = new FXMLLoader();
			iLoader.setLocation(this.getClass().getResource("TableView.fxml"));
			iLoader.setRoot(this);
			iLoader.setController(this);

			iLoader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
	}

	/**
	 * Return the filter property
	 *
	 * @return the filter property
	 */
	public StringProperty filterProperty() {
		return this.filter;
	}

	/**
	 * Return the {@link String} representation of the value at aRow and aColumn
	 *
	 * @param aRow
	 *            the row
	 * @param aColumn
	 *            the column
	 * @return the String representation of the value
	 */
	public String getTextAt(final int aRow, final int aColumn) {
		final Object aValue = this.getValueAt(aRow, aColumn);
		return aValue.toString();
	}

	/**
	 * Return the value at aRow and aColumn
	 *
	 * @param aRow
	 *            the row index
	 * @param aColumn
	 *            the column index
	 * @return the value or null
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValueAt(final int aRow, final int aColumn) {
		final TableColumn<S, T> iTableColumn = (TableColumn<S, T>) this.getColumns().get(aColumn);
		final T iCellData = iTableColumn.getCellData(aRow);
		if (iCellData != null) {
			return iCellData;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handle(final Event aEvent) {
		if (aEvent instanceof KeyEvent) {
			this.handleKeyEvent((KeyEvent) aEvent);
		} else if (aEvent instanceof CellEditEvent) {
			this.handleCellEditEvent((CellEditEvent<S, ?>) aEvent);
		}
	}

	private void handleCellEditCancel(final CellEditEvent<S, ?> aEvent) {
		// TODO Auto-generated method stub

	}

	private void handleCellEditCommit(final CellEditEvent<S, ?> aEvent) {
		final TablePosition<S, ?> iTablePosition = aEvent.getTablePosition();
		this.nextPosition = new TablePosition<>(this, iTablePosition.getRow(), this.getColumns().get(iTablePosition.getColumn() + 1));
		this.previousPosition = new TablePosition<>(this, iTablePosition.getRow(), this.getColumns().get(iTablePosition.getColumn() - 1));

		this.edit(this.nextPosition.getRow(), this.nextPosition.getTableColumn());
	}

	private void handleCellEditEvent(final CellEditEvent<S, ?> aEvent) {
		if (aEvent.getEventType().getName().equals("EDIT_START")) {
			this.handleCellEditStart(aEvent);
		} else if (aEvent.getEventType().getName().equals("EDIT_COMMIT")) {
			this.handleCellEditCommit(aEvent);
		} else if (aEvent.getEventType().getName().equals("EDIT_CANCEL")) {
			this.handleCellEditCancel(aEvent);
		}
	}

	private void handleCellEditStart(final CellEditEvent<S, ?> aEvent) {
		// TODO Auto-generated method stub

	}

	private void handleKeyEvent(final KeyEvent aEvent) {
		if (new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN).match(aEvent)) {
			final String iAnswer = JOptionPane.showInputDialog("N° de ligne:");
			if ((iAnswer != null) && !iAnswer.isEmpty()) {
				try {
					final int iRowIndex = Integer.parseInt(iAnswer);
					this.select(iRowIndex);
				} catch (final NumberFormatException aException) {
				}
			}
		} else if (new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN).match(aEvent)) {
			final String iAnswer = JOptionPane.showInputDialog("Rechercher:");
			this.select(iAnswer);
		} else if (new KeyCodeCombination(KeyCode.TAB).match(aEvent)) {
			if (this.nextPosition != null) {
				this.edit(this.nextPosition.getRow(), this.nextPosition.getTableColumn());
			}
		} else if (new KeyCodeCombination(KeyCode.ENTER).match(aEvent)) {
			if (this.nextPosition != null) {
				this.edit(this.nextPosition.getRow(), this.nextPosition.getTableColumn());
			}
		} else if (new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_ANY).match(aEvent)) {
			if (this.previousPosition != null) {
				this.edit(this.previousPosition.getRow(), this.previousPosition.getTableColumn());
			}

		} else if (new KeyCodeCombination(KeyCode.ESCAPE).match(aEvent)) {
			this.select(null);
		}
	}

	/**
	 * Return the index of the first row that match aPattern, else return -1
	 *
	 * @param aPattern
	 *            the pattern to search
	 * @return the index of the first row that match the pattern else return -1
	 */
	public int indexOf(final String aPattern) {
		if ((aPattern != null) && !aPattern.isEmpty()) {
			for (int iRow = 0; iRow < this.getItems().size(); iRow++) {
				for (int iColumn = 0; iColumn < this.getColumns().size(); iColumn++) {
					final String iText = this.getTextAt(iRow, iColumn);
					if (iText != null) {
						if ((iText != null) && iText.contains(aPattern)) {
							return iRow;
						}
					}
				}
			}
		}

		return -1;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onChanged(final javafx.collections.ListChangeListener.Change<? extends TableColumn<S, ?>> aChange) {
		if (aChange.next()) {
			if (aChange.wasRemoved()) {
			} else if (aChange.wasAdded()) {
				final List<? extends TableColumn<S, ?>> iAddedTableColumns = aChange.getAddedSubList();
				for (final TableColumn iTableColumn : iAddedTableColumns) {
					iTableColumn.setOnEditCommit(this);
					iTableColumn.setOnEditStart(this);
					iTableColumn.setOnEditCancel(this);
				}
			} else if (aChange.wasReplaced()) {
			} else if (aChange.wasUpdated()) {
			} else if (aChange.wasPermutated()) {
			}
		}
	}

	/**
	 * Select a row
	 *
	 * @param aRowToSelect
	 *            the row to select
	 */
	public void select(final int aRowToSelect) {
		this.getSelectionModel().select(aRowToSelect);
	}

	/**
	 * Select the first row that match aPattern.
	 *
	 * @param aPattern
	 *            the pattern to search
	 */
	public void select(final String aPattern) {
		final int iIndex = this.indexOf(aPattern);
		if (iIndex == -1) {
			this.getSelectionModel().select(null);
		} else {
			this.getSelectionModel().select(iIndex);
		}
	}

}
