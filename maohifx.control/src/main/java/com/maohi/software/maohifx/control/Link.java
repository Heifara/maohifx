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
import javafx.scene.control.Hyperlink;

/**
 * @author heifara
 *
 */
public class Link extends Hyperlink implements Initializable, EventHandler<ActionEvent> {

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

		try {
			final FXMLLoader iLoader = new FXMLLoader();
			iLoader.setLocation(this.getClass().getResource("Link.fxml"));
			iLoader.setRoot(this);
			iLoader.setController(this);

			iLoader.load();
		} catch (final IOException aException) {
			throw new RuntimeException(aException);
		}
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
	public void handle(final ActionEvent aEvent) {
		try {
			final FXMLLoader iLoader = new FXMLLoader();
			iLoader.setLocation(new URL(this.href));

			switch (this.target) {
			case BLANK:
				blankTarget.handle(this, aEvent, iLoader, null);
				break;

			case SELF:
				selfTarget.handle(this, aEvent, iLoader, null);
				break;

			case FRAMENAME:
				framenameTarget.handle(this, aEvent, iLoader, this.recipee);
				break;

			default:
				throw new IllegalArgumentException();
			}
		} catch (final Throwable aThrowable) {
			JOptionPane.showMessageDialog(null, aThrowable.getMessage());
		}
	}

	@Override
	public void initialize(final URL aLocation, final ResourceBundle aResources) {
		this.setOnAction(this);
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
