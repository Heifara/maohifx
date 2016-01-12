package com.maohi.software.maohifx.samples;

import java.awt.EventQueue;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

/**
 * @author Lawrence Dol
 * @see http://stackoverflow.com/users/8946/lawrence-dol
 */
public class TextAreaOutputStream extends OutputStream {

	// *************************************************************************************************
	// INSTANCE MEMBERS
	// *************************************************************************************************

	static class Appender implements Runnable {
		static private final String EOL1 = "\n";
		static private final String EOL2 = System.getProperty("line.separator", EOL1);
		private final JTextArea textArea;
		private final int maxLines; // maximum lines allowed in text area

		private final LinkedList<Integer> lengths; // length of lines within text area
		private final List<String> values; // values waiting to be appended
		private int curLength; // length of current line

		private boolean clear;

		private boolean queue;

		Appender(final JTextArea txtara, final int maxlin) {
			this.textArea = txtara;
			this.maxLines = maxlin;
			this.lengths = new LinkedList<Integer>();
			this.values = new ArrayList<String>();

			this.curLength = 0;
			this.clear = false;
			this.queue = true;
		}

		synchronized void append(final String val) {
			this.values.add(val);
			if (this.queue) {
				this.queue = false;
				EventQueue.invokeLater(this);
			}
		}

		synchronized void clear() {
			this.clear = true;
			this.curLength = 0;
			this.lengths.clear();
			this.values.clear();
			if (this.queue) {
				this.queue = false;
				EventQueue.invokeLater(this);
			}
		}

		// MUST BE THE ONLY METHOD THAT TOUCHES textArea!
		@Override
		public synchronized void run() {
			if (this.clear) {
				this.textArea.setText("");
			}
			for (final String val : this.values) {
				this.curLength += val.length();
				if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
					if (this.lengths.size() >= this.maxLines) {
						this.textArea.replaceRange("", 0, this.lengths.removeFirst());
					}
					this.lengths.addLast(this.curLength);
					this.curLength = 0;
				}
				this.textArea.append(val);
			}
			this.values.clear();
			this.clear = false;
			this.queue = true;
		}
	}

	static private String bytesToString(final byte[] ba, final int str, final int len) {
		try {
			return new String(ba, str, len, "UTF-8");
		} catch (final UnsupportedEncodingException thr) {
			return new String(ba, str, len);
		} // all JVMs are required to support UTF-8
	}

	private final byte[] oneByte; // array for write(int val);

	private Appender appender; // most recent action

	public TextAreaOutputStream(final JTextArea txtara) {
		this(txtara, 1000);
	}

	public TextAreaOutputStream(final JTextArea txtara, final int maxlin) {
		if (maxlin < 1) {
			throw new IllegalArgumentException("TextAreaOutputStream maximum lines must be positive (value=" + maxlin + ")");
		}
		this.oneByte = new byte[1];
		this.appender = new Appender(txtara, maxlin);
	}

	/** Clear the current console text area. */
	public synchronized void clear() {
		if (this.appender != null) {
			this.appender.clear();
		}
	}

	@Override
	public synchronized void close() {
		this.appender = null;
	}

	@Override
	public synchronized void flush() {
	}

	@Override
	public synchronized void write(final byte[] ba) {
		this.write(ba, 0, ba.length);
	}

	@Override
	public synchronized void write(final byte[] ba, final int str, final int len) {
		if (this.appender != null) {
			this.appender.append(bytesToString(ba, str, len));
		}
	}

	// *************************************************************************************************
	// STATIC MEMBERS
	// *************************************************************************************************

	@Override
	public synchronized void write(final int val) {
		this.oneByte[0] = (byte) val;
		this.write(this.oneByte, 0, 1);
	}

} /* END PUBLIC CLASS */