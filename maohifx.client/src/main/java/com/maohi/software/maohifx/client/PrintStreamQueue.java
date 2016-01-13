/**
 *
 */
package com.maohi.software.maohifx.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author heifara
 *
 */
public class PrintStreamQueue extends PrintStream {

	private final List<PrintStream> printStreams;

	public PrintStreamQueue(final OutputStream aOutputStream, final boolean aErrorStream) {
		super(aOutputStream);

		this.printStreams = new ArrayList<>();
		if (aErrorStream) {
			this.printStreams.add(System.err);
		} else {
			this.printStreams.add(System.out);
		}

		this.printStreams.add(new PrintStream(aOutputStream));
	}

	@Override
	synchronized public void write(final byte[] aB) throws IOException {
		for (final PrintStream iPrintStream : this.printStreams) {
			iPrintStream.write(aB);
		}
	}

	@Override
	synchronized public void write(final byte[] aBuf, final int aOff, final int aLen) {
		for (final PrintStream iPrintStream : this.printStreams) {
			iPrintStream.write(aBuf, aOff, aLen);
		}
	}

	@Override
	synchronized public void write(final int aB) {
		for (final PrintStream iPrintStream : this.printStreams) {
			iPrintStream.write(aB);
		}

	}

}
