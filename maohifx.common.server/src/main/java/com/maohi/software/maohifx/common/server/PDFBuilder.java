/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.StreamingOutput;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

/**
 * @author heifara
 *
 */
public interface PDFBuilder {

	/**
	 * Return the generated PDF File
	 *
	 * @return the generated pdf file
	 * @throws FOPException
	 * @throws IOException
	 * @throws TransformerException
	 */
	File build() throws FOPException, IOException, TransformerException;

	/**
	 * The entity transformed into a JaxB Object
	 *
	 * @param aElement
	 *            the element to transform in JaxB
	 * @return the current PDFBuilder
	 * @see AbstractPDFBuilder#toJaxb(Object)
	 */
	PDFBuilder entity(Object aElement);

	/**
	 * Return the output
	 *
	 * @return the output
	 * @throws FOPException
	 * @throws IOException
	 * @throws TransformerException
	 */
	StreamingOutput output() throws FOPException, IOException, TransformerException;

}
