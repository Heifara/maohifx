/**
 *
 */
package com.maohi.software.maohifx.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.maohi.software.maohifx.control.enumerations.HrefTarget;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * @author heifara
 *
 */
public class Link extends Hyperlink implements Initializable, EventHandler<MouseEvent> {

	/**
	 * @author heifara
	 *
	 */
	public static interface LinkTarget {

		/**
		 * Executed when an error occurred
		 *
		 * @param aLink
		 *            the link component
		 * @param aEvent
		 *            the event from the {@link EventHandler}
		 * @param aException
		 *            the exception
		 */
		void error(Link aLink, ActionEvent aEvent, Throwable aException);

		/**
		 * Executed when success
		 *
		 * @param aLink
		 *            the link component
		 * @param aEvent
		 *            the event from the {@link EventHandler}
		 * @param aLoader
		 *            the loader used to locate the item
		 * @param aRecipeId
		 *            the {@link Node} id where the {@link Node} should be display. <br>
		 *            Will be null in all {@link HrefTarget} except {@link HrefTarget#FRAMENAME}
		 */
		void handle(Link aLink, ActionEvent aEvent, FXMLLoader aLoader, String aRecipeId);

	}

	private static LinkTarget blankTarget;
	private static LinkTarget selfTarget;
	private static LinkTarget framenameTarget;

	public static void setHrefTarget(final HrefTarget aHrefTarget, final LinkTarget aLinkTarget) {
		switch (aHrefTarget) {
		case BLANK:
			blankTarget = aLinkTarget;
			break;

		case SELF:
			selfTarget = aLinkTarget;
			break;

		case FRAMENAME:
			framenameTarget = aLinkTarget;
			break;

		default:
			throw new IllegalArgumentException();
		}
	}

	private String href;

	private HrefTarget target;

	private String recipee;

	public Link() {
		this.target = HrefTarget.SELF;

		this.getStyleClass().add("link");

		try {
			final FXMLLoader iLoader = new FXMLLoader();
			iLoader.setLocation(this.getClass().getResource("Link.fxml"));
			iLoader.setRoot(this);
			iLoader.setController(this);

			iLoader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}

		this.setTooltip(new Tooltip(this.getText()));
		this.tooltipProperty().get().textProperty().bindBidirectional(this.textProperty());

	}

	public Link(final String aText) {
		this();
		this.setText(aText);
	}

	public String getHref() {
		return this.href;
	}

	public String getRecipee() {
		return this.recipee;
	}

	public HrefTarget getTarget() {
		return this.target;
	}

	@Override
	public String getUserAgentStylesheet() {
		return Link.class.getResource("link.css").toExternalForm();
	}

	@Override
	public void handle(final MouseEvent aEvent) {
		try {
			if (aEvent.getButton().equals(MouseButton.PRIMARY) || aEvent.getButton().equals(MouseButton.MIDDLE)) {
				final FXMLLoader iLoader = new FXMLLoader();
				iLoader.setLocation(new URL(this.href));

				final HrefTarget iOldTarget = this.getTarget();
				if (aEvent.isControlDown() || aEvent.getButton().equals(MouseButton.MIDDLE)) {
					this.setTarget(HrefTarget.BLANK);
				}

				switch (this.target) {
				case BLANK:
					blankTarget.handle(this, new ActionEvent(), iLoader, null);
					break;

				case SELF:
					selfTarget.handle(this, new ActionEvent(), iLoader, null);
					break;

				case FRAMENAME:
					framenameTarget.handle(this, new ActionEvent(), iLoader, this.recipee);
					break;

				default:
					throw new IllegalArgumentException();
				}

				this.setTarget(iOldTarget);
			}
		} catch (final Throwable aThrowable) {
			JOptionPane.showMessageDialog(null, aThrowable.getMessage());
		}
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.setOnMouseClicked(this);
	}

	public void setHref(final String aHref) {
		this.href = aHref;
	}

	public void setRecipee(final String recipee) {
		this.recipee = recipee;
	}

	public void setTarget(final HrefTarget target) {
		this.target = target;
	}

}
