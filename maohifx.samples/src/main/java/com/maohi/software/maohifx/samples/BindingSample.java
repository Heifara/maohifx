/**
 *
 */
package com.maohi.software.maohifx.samples;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author heifara
 *
 */
public class BindingSample {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final BooleanProperty iBooleanProperty = new SimpleBooleanProperty();
		System.out.println(iBooleanProperty.get());

		final StringProperty iStringOne = new SimpleStringProperty("Hello ");
		final StringProperty iStringTwo = new SimpleStringProperty("World");
		final StringProperty iConcat = new SimpleStringProperty();
		iConcat.bind(Bindings.concat(iStringOne, iStringTwo));

		System.out.println(iConcat.get());

		final SimpleDoubleProperty num1 = new SimpleDoubleProperty(1.0);
		final IntegerProperty num2 = new SimpleIntegerProperty(2);
		final IntegerProperty num3 = new SimpleIntegerProperty(3);
		final IntegerProperty num4 = new SimpleIntegerProperty(4);
		final NumberBinding total = Bindings.add(num1.multiply(num2), num3.multiply(num4));

		System.out.println(total.getValue());

		num1.set(2);
		System.out.println(total.getValue());
	}

}
