/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heifara
 *
 */
public class PDFBuilderFactory {

	private static PDFBuilderFactory singleton;

	public static PDFBuilderFactory getInstance() {
		if (singleton == null) {
			singleton = new PDFBuilderFactory();
		}
		return singleton;
	}

	public static PDFBuilder getInstance(final String aEntity) throws InstantiationException, IllegalAccessException {
		return getInstance().get(aEntity);
	}

	private final Map<String, Class<? extends PDFBuilder>> builderClasses;

	private final Map<String, PDFBuilder> pdfBuilders;

	public PDFBuilderFactory() {
		this.builderClasses = new HashMap<>();
		this.pdfBuilders = new HashMap<>();
	}

	private PDFBuilder get(final String aEntity) throws InstantiationException, IllegalAccessException {
		PDFBuilder iBuilder = this.pdfBuilders.get(aEntity);
		if (iBuilder == null) {
			final Class<? extends PDFBuilder> iClass = this.builderClasses.get(aEntity);
			if (iClass != null) {
				iBuilder = iClass.newInstance();
				this.pdfBuilders.put(aEntity, iBuilder);
			}
		}
		return iBuilder;
	}

	public void setBuilder(final String aEntityName, final Class<? extends PDFBuilder> aClass) {
		this.builderClasses.put(aEntityName, aClass);
	}

}
