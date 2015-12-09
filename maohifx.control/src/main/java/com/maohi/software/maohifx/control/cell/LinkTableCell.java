/**
 *
 */
package com.maohi.software.maohifx.control.cell;

import java.util.Map;

import com.maohi.software.maohifx.control.Link;
import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.util.StringConverter;
import netscape.javascript.JSObject;

/**
 * @author heifara
 *
 */
public class LinkTableCell<S, T> extends TableCell<S, T> {

	private final String property;
	private final HrefTarget target;
	private final StringConverter<T> converter;

	private Link link;

	public LinkTableCell(final String aProperty, final HrefTarget aTarget, final StringConverter<T> aConverter) {
		this.property = aProperty;
		this.target = aTarget;
		this.converter = aConverter;
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		this.getLink().setText(this.converter.toString(this.getItem()));
		this.setGraphic(this.getLink());
	}

	private String getHref(final Object iItem) {
		SimpleStringProperty iHref = new SimpleStringProperty();
		if (iItem instanceof Map) {
			final Map<?, ?> iData = (Map<?, ?>) iItem;
			iHref = (SimpleStringProperty) iData.get(this.property);
		} else {
			final JSObject iData = (JSObject) iItem;
			final ReadOnlyObjectWrapper<T> iReadOnlyObject = new ReadOnlyObjectWrapper<T>(iData.getMember(this.getProperty()) != null ? (T) iData.getMember(this.getProperty()) : null);
			iHref = new SimpleStringProperty();
			iHref.set(iReadOnlyObject.asString().get());
		}
		return iHref.get();
	}

	public Link getLink() {
		if (this.link == null) {
			this.link = new Link();
		}
		return this.link;
	}

	public String getProperty() {
		return this.property;
	}

	@Override
	public void startEdit() {
		if (!this.isEditable() || !this.getTableView().isEditable() || !this.getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();

		this.setText(null);

		final TableRow<?> iTableRow = (TableRow<?>) this.getParent();
		this.getLink().setHref(this.getHref(iTableRow.getItem()));
		this.getLink().setTarget(this.target);
		this.setGraphic(this.getLink());
	}

	@Override
	protected void updateItem(final T aItem, final boolean aEmpty) {
		super.updateItem(aItem, aEmpty);

		if (aEmpty) {
			this.setText(null);
			this.getLink().setText(null);
			this.setGraphic(null);
		} else {
			this.setText(this.converter.toString(aItem));
			this.getLink().setText(this.converter.toString(aItem));
			this.getLink().setTarget(this.target);
		}
	}

}
