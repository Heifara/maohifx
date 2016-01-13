/**
 *
 */
package com.maohi.software.maohifx.samples;

import org.junit.Test;

/**
 * @author heifara
 *
 */
public class SpoolerSampleTest {

	@Test
	public void test() throws Exception {
		final SpoolerSample iSpooler = new SpoolerSample();
		iSpooler.runLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Running 1");
				for (int iI = 0; iI < 100000; iI++) {
					System.out.print("\r");
					System.out.print(iI);
				}

				System.out.println();
			}
		});
		iSpooler.runLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Running 2");
				for (int iI = 0; iI < 1000000; iI++) {
					System.out.print("\r");
					System.out.print(iI);
				}
				System.out.println();
			}
		});

		Thread.sleep(100);
		iSpooler.runLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Running 3");
				for (int iI = 0; iI < 1000; iI++) {
					System.out.print("\r");
					System.out.print(iI);
				}
				System.out.println();
			}
		});

		Thread.sleep(5000);
	}

}
