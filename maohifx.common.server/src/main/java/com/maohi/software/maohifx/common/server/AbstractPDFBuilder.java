/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;

/**
 * @author heifara
 *
 */
public abstract class AbstractPDFBuilder<E> implements PDFBuilder {

	/**
	 * @author heifara
	 *
	 */
	protected class ByteStreamingOutput implements StreamingOutput {

		private final File file;

		public ByteStreamingOutput(final File aFile) {
			this.file = aFile;
		}

		@Override
		public void write(final OutputStream aOutput) throws IOException, WebApplicationException {
			final InputStream iInputStream = new FileInputStream(this.file);

			try {
				final OutputStream iOutputStream = new BufferedOutputStream(aOutput);

				int iRead = 0;
				final byte[] iBytes = new byte[1024];

				while ((iRead = iInputStream.read(iBytes)) != -1) {
					iOutputStream.write(iBytes, 0, iRead);
					iOutputStream.flush();
				}

				iInputStream.close();
			} catch (final IOException aException) {
				aException.printStackTrace();
			}
		}

	}

	private Object element;

	@SuppressWarnings("unchecked")
	@Override
	public File build() throws IOException, TransformerException, FOPException {
		// Generate the data source ".xml" from Element
		final File iXmlFile = File.createTempFile("maohifx", ".xml");
		this.writeXML(this.toJaxb((E) this.element), new FileOutputStream(iXmlFile), this.getJaxbPackage(), this.getClass().getClassLoader());

		// Create Transformer
		final TransformerFactory iTransformerFactory = TransformerFactory.newInstance();

		// Create FO
		final File iFoFile = File.createTempFile("maohifx", ".fo");
		final OutputStream iFOOutputStream = new FileOutputStream(iFoFile);
		final Transformer iFoTransformer = iTransformerFactory.newTransformer(new StreamSource(this.getXslInputStream(this.element)));
		iFoTransformer.transform(new StreamSource(new FileInputStream(iXmlFile)), new StreamResult(iFOOutputStream));
		iFOOutputStream.close();

		// Create PDF
		final File iPdfFile = File.createTempFile("maohifx", ".pdf");
		final FopFactory iFopFactory = FopFactory.newInstance(new File(".").toURI());
		final OutputStream aPDFOutputStream = new FileOutputStream(iPdfFile);
		final FOUserAgent iFOUserAgent = iFopFactory.newFOUserAgent();
		final Fop iFop = iFopFactory.newFop(org.apache.xmlgraphics.util.MimeConstants.MIME_PDF, iFOUserAgent, aPDFOutputStream);
		final Transformer iPdfTransformer = iTransformerFactory.newTransformer();
		iPdfTransformer.transform(new StreamSource(iFoFile), new SAXResult(iFop.getDefaultHandler()));
		aPDFOutputStream.close();
		return iPdfFile;
	}

	@Override
	public PDFBuilder entity(final Object aElement) {
		this.element = aElement;
		return this;
	}

	protected abstract String getJaxbPackage();

	protected abstract InputStream getXslInputStream(final Object aElement);

	@Override
	public StreamingOutput output() throws FOPException, IOException, TransformerException {
		return new ByteStreamingOutput(this.build());
	}

	protected abstract Object toJaxb(final E aElement);

	/**
	 * Write an xml file
	 *
	 * @param aElement
	 *            the object to export to Xml
	 * @param aOutputStream
	 *            the outputstream
	 * @param aPackage
	 *            the package where the ObjectFactory is
	 * @param aClassLoader
	 *            the ClassLoader use during the newInstance()
	 */
	public void writeXML(final Object aElement, final OutputStream aOutputStream, final String aPackage, final ClassLoader aClassLoader) {
		try {
			final JAXBContext iJaxbContext = JAXBContext.newInstance(aPackage, aClassLoader);
			final Marshaller iMarshaller = iJaxbContext.createMarshaller();
			iMarshaller.marshal(aElement, aOutputStream);
		} catch (final JAXBException aException) {
			aException.printStackTrace();
		}
	}

}
