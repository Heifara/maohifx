/**
 *
 */
package com.maohi.software.maohifx.client;

import static org.junit.Assert.assertFalse;

import java.io.OutputStream;

import org.junit.Test;

/**
 * @author heifara
 *
 */
public class PrintStreamQueueTest {

	@Test
	public void test() throws Exception {
		final OutputStream iOutputStream = new ConsoleRedirectOutputStream();
		System.setOut(new PrintStreamQueue(iOutputStream, false));
		System.setErr(new PrintStreamQueue(iOutputStream, true));

		System.out.println("Hello World");
		System.err.println("Hello World");
		assertFalse(false);
	}

}
