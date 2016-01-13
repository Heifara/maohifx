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
	static private final String EOL1 = "\n";
	static private final String EOL2 = System.getProperty("line.separator", EOL1);

	private final LinkedList<Integer> lengths; // length of lines within text area
	private final List<String> values; // values waiting to be appended
	private int maxLines; // maximum lines allowed in text area
	private int curLength; // length of current line

	private boolean clear;

	private boolean queue;

	public TextAreaConsoleRedirectAppender() {
		this.lengths = new LinkedList<Integer>();
		this.values = new ArrayList<String>();

		this.curLength = 0;
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
		this.curLength = 0;
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
			this.curLength += val.length();
			if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
				if (this.lengths.size() >= this.maxLines) {
					this.replaceText(0, this.lengths.removeFirst(), "");
				}
				this.lengths.addLast(this.curLength);
				this.curLength = 0;
			}
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
