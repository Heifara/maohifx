/**
 *
 */
package com.maohi.software.maohifx.client;

import org.controlsfx.control.textfield.CustomTextField;

import com.sun.javafx.fxml.builder.ProxyBuilder;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

/**
 * @author heifara
 *
 */
@SuppressWarnings("restriction")
public class MaohiFXBuilderFactory implements BuilderFactory {

	private final BuilderFactory baseFactory;

	public MaohiFXBuilderFactory() {
		this.baseFactory = new JavaFXBuilderFactory();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Builder<?> getBuilder(final Class<?> aType) {
		if (TextField.class.equals(aType)) {
			return new ProxyBuilder<>(CustomTextField.class);
		} else if (TableView.class.equals(aType)) {
			return new ProxyBuilder(com.maohi.software.maohifx.control.TableView.class);
		}

		return this.baseFactory.getBuilder(aType);
	}

}
