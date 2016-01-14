/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author heifara
 *
 */
public class MyApplication extends ResourceConfig {

	private static List<Runnable> runLater = new ArrayList<>();

	public static void runLater(final Runnable aRunnable) {
		runLater.add(aRunnable);
	}

	public MyApplication() {
		System.setProperty("java.io.tmpdir", "E:\\Temp");

		this.fireRunLater();
	}

	private void fireRunLater() {
		for (final Runnable iRunnable : runLater) {
			final Thread iThread = new Thread(iRunnable);
			iThread.start();
		}
	}
}
