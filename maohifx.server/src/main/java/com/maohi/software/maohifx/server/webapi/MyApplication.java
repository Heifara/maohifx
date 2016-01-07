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

	public MyApplication() {
		System.setProperty("java.io.tmpdir", "C:\\temp");

		this.fireRunLater();
	}

	private void fireRunLater() {
		for (Runnable iRunnable : runLater) {
			Thread iThread = new Thread(iRunnable);
			iThread.start();
		}
	}

	public static void runLater(Runnable aRunnable) {
		runLater.add(aRunnable);
	}
}
