/**
 *
 */
package com.maohi.software.maohifx.samples;

import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * @author heifara
 *
 */
public class TextAreaOutputStreamTest {

	public static void main(final String[] args) {
		final JTextArea iTextArea = new JTextArea();

		final PrintStream con = new PrintStream(new TextAreaOutputStream(iTextArea));
		System.setOut(con);
		System.setErr(con);

		final JFrame iFrame = new JFrame("Test Console");
		iFrame.getContentPane().add(iTextArea);
		iFrame.setSize(800, 600);
		iFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iFrame.setVisible(true);

		System.out.println("Hello World");
		System.out.println("Hello World");
		System.out.println("Hello World");
	}

}
