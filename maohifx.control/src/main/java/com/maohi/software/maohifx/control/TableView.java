/**
 *
 */
package com.maohi.software.maohifx.control;

import javafx.scene.control.TableColumn;

/**
 * @author heifara
 *
 */
public class TableView<S> extends javafx.scene.control.TableView<S> {

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

	/**
	 * Return the index of the first row that match aPattern, else return -1
	 *
	 * @param aPattern
	 *            the pattern to search
	 * @return the index of the first row that match the pattern else return -1
	 */
	public int indexOf(final String aPattern) {
		if (aPattern != null) {
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
