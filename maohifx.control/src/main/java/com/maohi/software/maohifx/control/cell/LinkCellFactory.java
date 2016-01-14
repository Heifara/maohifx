/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import com.maohi.software.maohifx.common.ExtStringConverter;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.beans.NamedArg;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author heifara
 *
 */
public class LinkCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	private final String property;
	private final HrefTarget target;

	public LinkCellFactory(@NamedArg("property") final String aProperty) {
		this(aProperty, HrefTarget.SELF.name());
	}

	public LinkCellFactory(@NamedArg("property") final String aProperty, @NamedArg("target") final String aTarget) {
		this.property = aProperty;
		this.target = HrefTarget.valueOf(aTarget);
	}

	@Override
	public TableCell<S, T> call(final TableColumn<S, T> param) {
		return new LinkTableCell<S, T>(this.property, this.target, new ExtStringConverter<T>());
	}

}
