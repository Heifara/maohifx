/**
 * 
 */
package com.maohi.software.maohifx.samples.apachefop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.SAXException;

/**
 * @author heifara
 *
 */
public class ApacheFOPSample {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws URISyntaxException
	 * @throws TransformerFactoryConfigurationError
	 */
	public static void main(final String[] args) {
		new ApacheFOPSample().doMain(args);
	}

	/**
	 * @param aArgs
	 * @throws SAXException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws FOPException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws URISyntaxException
	 */
	private void doMain(final String[] aArgs) {
		try {
			// Step 1: Construct a FopFactory by specifying a reference to the configuration file
			// (reuse if you plan to render multiple documents!)
			final FopFactory iFopFactory = FopFactory.newInstance(this.getClass().getResource("fop.xconf").toURI());

			// Step 2: Set up output stream.
			// Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
			final File iFile = new File("src/main/resources/myfile.pdf");
			iFile.createNewFile();

			try (final OutputStream iOutputStream = new BufferedOutputStream(new FileOutputStream(iFile));) {
				// Step 3: Construct fop with desired output format
				final Fop iFop = iFopFactory.newFop(MimeConstants.MIME_PDF, iOutputStream);

				// Step 4: Setup JAXP using identity transformer
				final TransformerFactory iTransformerFactory = TransformerFactory.newInstance();
				final Transformer iTransformer = iTransformerFactory.newTransformer(); // identity transformer

				// Step 5: Setup input and output for XSLT transformation
				// Setup input stream
				final Source iSource = new StreamSource(new File(this.getClass().getResource("myfile.fo.xml").toURI()));

				// Resulting SAX events (the generated FO) must be piped through to FOP
				final Result iResult = new SAXResult(iFop.getDefaultHandler());

				// Step 6: Start XSLT transformation and FOP processing
				iTransformer.transform(iSource, iResult);
			} catch (final Exception aException) {
				aException.printStackTrace();
			}

			java.lang.Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + iFile.getAbsolutePath());

		} catch (final Exception aException) {
			aException.printStackTrace();
		}

	}

}
