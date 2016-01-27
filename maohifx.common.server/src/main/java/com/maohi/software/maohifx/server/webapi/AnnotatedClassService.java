/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.common.server.AnnotatedClass;
import com.maohi.software.maohifx.common.server.HibernateUtil;

/**
 * @author heifara
 *
 */
public abstract class AnnotatedClassService<A extends AbstractDAO<T>, T extends AnnotatedClass> extends RestService {

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

	protected final A dao;

	@SuppressWarnings("static-access")
	public AnnotatedClassService() throws InstantiationException, IllegalAccessException {
		this.dao.setSession(HibernateUtil.getSessionFactory().openSession());

		this.dao = this.getDAOClass().newInstance();
	}

	abstract Class<T> getAnnotatedClass();

	abstract Class<A> getDAOClass();

	@RolesAllowed("user")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFromQueryParam(@QueryParam("uuid") final String aUuid) {
		try {
			final T iElement = this.dao.read(aUuid);
			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final IOException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	protected abstract String getJaxbPackage();

	protected abstract InputStream getXslInputStream(T iElement);

	public abstract void onInserted(T iElement);

	public abstract void onInserting(T iElement);

	public abstract void onSaved(T iElement);

	public abstract void onSaving(T iElement);

	public abstract void onUpdated(T iElement);

	public abstract void onUpdating(T iElement);

	@Path("pdf")
	@GET
	@Produces({ "application/pdf" })
	public Response pdfFromQueryParam(@QueryParam("uuid") final String aUuid) {
		final T iElement = this.dao.read(aUuid);

		try {
			// Generate the data source ".xml" from Element
			final File iXmlFile = File.createTempFile("maohifx", ".xml");
			this.writeXML(this.toJaxb(iElement), new FileOutputStream(iXmlFile), this.getJaxbPackage(), this.getClass().getClassLoader());

			// Create Transformer
			final TransformerFactory iTransformerFactory = TransformerFactory.newInstance();

			// Create FO
			final File iFoFile = File.createTempFile("maohifx", ".fo");
			final OutputStream iFOOutputStream = new FileOutputStream(iFoFile);
			final Transformer iFoTransformer = iTransformerFactory.newTransformer(new StreamSource(this.getXslInputStream(iElement)));
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

			return Response.ok(new ByteStreamingOutput(iPdfFile)).build();
		} catch (FOPException | IOException | TransformerException aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RolesAllowed("user")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response save(final String aJSONObject) {
		T iElement;
		try {
			iElement = new ObjectMapper().readValue(aJSONObject, this.getAnnotatedClass());

			this.onSaving(iElement);

			if (!this.dao.exists(iElement.getUuid())) {
				if (iElement.getUuid() == null) {
					iElement.setUuid(UUID.randomUUID().toString());
				}
				iElement.setCreationDate(new Date());
				iElement.setUpdateDate(new Date());

				this.dao.beginTransaction();
				this.onInserting(iElement);
				this.dao.insert(iElement);
				this.onInserted(iElement);
				this.dao.commit();
			} else {
				iElement.setUpdateDate(new Date());

				this.dao.beginTransaction();
				this.onUpdating(iElement);
				this.dao.update(iElement);
				this.onUpdated(iElement);
				this.dao.commit();
			}

			this.onSaved(iElement);

			final String iJSONObject = new ObjectMapper().writeValueAsString(iElement);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	abstract public List<T> search(String aPattern);

	@RolesAllowed("user")
	@Path("search")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchFromQueryParam(@QueryParam("pattern") final String aPattern) {
		try {
			final List<T> iElements = this.search(aPattern);
			for (final T iElement : iElements) {
				iElement.setHref(this.getLocalContextUri() + "/webapi/" + this.getAnnotatedClass().getSimpleName().toLowerCase() + "?uuid=" + iElement.getUuid());
			}
			final String iJSONObject = new ObjectMapper().writeValueAsString(iElements);
			return Response.ok(iJSONObject).build();
		} catch (final Exception aException) {
			aException.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	protected abstract Object toJaxb(final T iElement);

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
