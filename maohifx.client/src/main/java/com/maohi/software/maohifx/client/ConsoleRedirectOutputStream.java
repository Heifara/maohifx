/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on the work of Lawrence Dol
 *
 * @author heifara
 * @see http://stackoverflow.com/users/8946/lawrence-dol
 *
 */
public class ConsoleRedirectOutputStream extends OutputStream {

	static private String bytesToString(final byte[] ba, final int str, final int len) {
		try {
			return new String(ba, str, len, "UTF-8");
		} catch (final UnsupportedEncodingException thr) {
			return new String(ba, str, len);
		} // all JVMs are required to support UTF-8
	}

	private final byte[] oneByte; // array for write(int val);

	private final List<ConsoleRedirectAppender> appenders;

	// private ConsoleRedirectAppender appender; // most recent action

	public ConsoleRedirectOutputStream() {
		this.oneByte = new byte[1];

		this.appenders = new ArrayList<>();
	}

	public void add(final ConsoleRedirectAppender aAppender) {
		this.appenders.add(aAppender);
	}

	public synchronized void clear() {
		for (final ConsoleRedirectAppender aConsoleRedirectAppender : this.appenders) {
			if (aConsoleRedirectAppender != null) {
				aConsoleRedirectAppender.clear();
			}
		}
	}

	@Override
	public synchronized void close() {
		this.appenders.clear();
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
		for (final ConsoleRedirectAppender aConsoleRedirectAppender : this.appenders) {
			if (aConsoleRedirectAppender != null) {
				aConsoleRedirectAppender.append(bytesToString(ba, str, len));
			}
		}
	}

	@Override
	public synchronized void write(final int val) {
		this.oneByte[0] = (byte) val;
		this.write(this.oneByte, 0, 1);
	}

}
