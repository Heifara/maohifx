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
import java.io.OutputStream;

/**
 * @author heifara
 *
 */
public class Files {

	public static File createTmpFile(InputStream aInputStream, String aPrefix, String aSuffixe) throws IOException {
		File iFile = null;
		try {
			iFile = File.createTempFile(aPrefix, aSuffixe);
		} catch (IllegalArgumentException aException) {
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

	public static String toString(File iFile) {
		StringBuilder iResult = new StringBuilder();
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(iFile));

			while ((sCurrentLine = br.readLine()) != null) {
				iResult.append(sCurrentLine);
				iResult.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return iResult.toString();
	}

}
