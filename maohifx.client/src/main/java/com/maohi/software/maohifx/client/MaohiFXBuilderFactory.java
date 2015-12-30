/**
 *
 */
package com.maohi.software.maohifx.client;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

/**
 * @author heifara
 *
 */
public class MaohiFXBuilderFactory implements BuilderFactory {

	private final BuilderFactory baseFactory;

	public MaohiFXBuilderFactory() {
		this.baseFactory = new JavaFXBuilderFactory();
	}

	@Override
	public Builder<?> getBuilder(final Class<?> aType) {
		return this.baseFactory.getBuilder(aType);
	}

}
