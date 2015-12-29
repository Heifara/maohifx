/**
 *
 */
package com.maohi.software.maohifx.common;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author heifara
 *
 */
public class JaxbUtils {

	/**
	 * Read the xml file "aXMLFilePath" and return the right object.
	 *
	 * @param aInputStream
	 *            The input stream.
	 * @param aPackage
	 *            The package "aPackage" to create the correct objects.
	 * @param aClassLoader
	 *            The class loader "aClassLoader" to create a new instance of JAXBContext.
	 * @return An object or null if an exception occurred.
	 * @throws JAXBException
	 */
	public static Object readXML(final InputStream aInputStream, final String aPackage, final ClassLoader aClassLoader) throws JAXBException {
		final JAXBContext iJAXBContext = JAXBContext.newInstance(aPackage, aClassLoader);
		final Unmarshaller iUnmarshaller = iJAXBContext.createUnmarshaller();
		final Object iObject = iUnmarshaller.unmarshal(aInputStream);
		return iObject;
	}

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
	 * @throws JAXBException
	 */
	public static void writeXML(final Object aElement, final OutputStream aOutputStream, final String aPackage, final ClassLoader aClassLoader) throws JAXBException {
		final JAXBContext iJaxbContext = JAXBContext.newInstance(aPackage, aClassLoader);
		final Marshaller iMarshaller = iJaxbContext.createMarshaller();
		iMarshaller.marshal(aElement, aOutputStream);
	}

}
