/**
 *
 */
package com.maohi.software.maohifx.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author heifara
 *
 */
public class Files {

	public static File createTmpFile(final InputStream aInputStream, final String aPrefix, final String aSuffixe) throws IOException {
		File iFile = null;
		try {
			iFile = File.createTempFile(aPrefix, aSuffixe);
		} catch (final IllegalArgumentException aException) {
			iFile = File.createTempFile("MLI", aSuffixe);
		}

		final OutputStream iOutputStream = new FileOutputStream(iFile);

		int iRead = 0;
		final byte[] iBytes = new byte[1024];

		while ((iRead = aInputStream.read(iBytes)) != -1) {
			iOutputStream.write(iBytes, 0, iRead);
		}

		iOutputStream.close();
		iFile.createNewFile();

		return iFile;
	}

	public static boolean isImage(final File aFile) {
		if (aFile.getPath().endsWith(".png")) {
			return true;
		} else if (aFile.getPath().endsWith(".jpg")) {
			return true;
		} else if (aFile.getPath().endsWith(".jpeg")) {
			return true;
		} else if (aFile.getPath().endsWith(".gif")) {
			return true;
		}

		return false;
	}

	public static String toString(final File iFile) {
		final StringBuilder iResult = new StringBuilder();
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(iFile));

			while ((sCurrentLine = br.readLine()) != null) {
				iResult.append(sCurrentLine);
				iResult.append("\n");
			}

		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return iResult.toString();
	}

	public static String toString(final URL aUrl) throws IOException {
		final StringBuilder iResult = new StringBuilder();

		final InputStream aInputStream = aUrl.openStream();
		final BufferedReader iBufferedReader = new BufferedReader(new InputStreamReader(aInputStream, "UTF8"));
		String iLine;

		while ((iLine = iBufferedReader.readLine()) != null) {
			iResult.append(iLine);
			iResult.append("\n");
		}

		aInputStream.close();

		return iResult.toString();
	}

}
