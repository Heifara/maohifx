/**
 *
 */
package com.maohi.software.maohifx.samples;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heifara
 *
 */
public class SpoolerSample implements Runnable {

	public List<Runnable> pendingRunnables;

	public SpoolerSample() {
		this.pendingRunnables = new ArrayList<>();
	}

	@Override
	public synchronized void run() {
		try {
			final List<Runnable> iRunningRunnables = new ArrayList<>();
			iRunningRunnables.addAll(this.pendingRunnables);
			for (final Runnable iRunnable : iRunningRunnables) {
				iRunnable.run();
			}

			this.pendingRunnables.removeAll(iRunningRunnables);
		} catch (final Exception aException) {
			aException.printStackTrace();
		}
	}

	public void runLater(final Runnable aRunnable) {
		System.out.println("Adding " + aRunnable.getClass().getSimpleName());
		this.pendingRunnables.add(aRunnable);

		final Thread iThread = new Thread(this);
		iThread.setName(this.getClass().getSimpleName());
		iThread.start();
	}

}
