/**
 *
 */
package com.maohi.software.maohifx.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * @author heifara
 *
 */
public class TextAreaConsoleRedirectAppender extends TextArea implements ConsoleRedirectAppender, Runnable {
	private final LinkedList<Integer> lengths; // length of lines within text area
	private final List<String> values; // values waiting to be appended
	private int maxLines; // maximum lines allowed in text area

	private boolean clear;

	private boolean queue;

	public TextAreaConsoleRedirectAppender() {
		this.lengths = new LinkedList<Integer>();
		this.values = new ArrayList<String>();

		this.clear = false;
		this.queue = true;
	}

	@Override
	public synchronized void append(final String aValue) {
		this.values.add(aValue);
		if (this.queue) {
			this.queue = false;
			Platform.runLater(this);
		}
	}

	@Override
	public synchronized void clear() {
		this.clear = true;
		this.lengths.clear();
		this.values.clear();
		if (this.queue) {
			this.queue = false;
			Platform.runLater(this);
		}
	}

	public int getMaxLines() {
		return this.maxLines;
	}

	@Override
	public synchronized void run() {
		if (this.clear) {
			this.setText("");
		}
		for (final String val : this.values) {
			this.appendText(val);
		}
		this.values.clear();
		this.clear = false;
		this.queue = true;
	}

	public void setMaxLines(final int aMaxLines) {
		this.maxLines = aMaxLines;
	}
}
