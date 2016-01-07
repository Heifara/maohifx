/**
 *
 */
package com.maohi.software.maohifx.client;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author heifara
 *
 */
public class Browser extends Region {

	private ObjectProperty<WebView> webViewProperty;

	@Override
	protected double computePrefHeight(final double width) {
		return 500;
	}

	@Override
	protected double computePrefWidth(final double height) {
		return 750;
	}

	public WebEngine getWebEngine() {
		return this.webViewProperty().get().getEngine();
	}

	public WebView getWebView() {
		return this.webViewProperty().get();
	}

	@Override
	protected void layoutChildren() {
		final double w = this.getWidth();
		final double h = this.getHeight();

		this.layoutInArea(this.getWebView(), 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	public void load(final String aUrl) {
		this.getWebEngine().getCreatePopupHandler();
		this.getWebView().getEngine().load(aUrl);
	}

	protected ObjectProperty<WebView> webViewProperty() {
		if (this.webViewProperty == null) {
			this.webViewProperty = new SimpleObjectProperty<WebView>();
			this.webViewProperty.addListener(new ChangeListener<WebView>() {

				@Override
				public void changed(final ObservableValue<? extends WebView> aObservable, final WebView aOldValue, final WebView aNewValue) {
					aNewValue.getEngine().setJavaScriptEnabled(false);

					Browser.this.getChildren().remove(aOldValue);
					Browser.this.getChildren().add(aNewValue);
				}
			});

			this.webViewProperty.set(new WebView());
		}
		return this.webViewProperty;
	}

}
