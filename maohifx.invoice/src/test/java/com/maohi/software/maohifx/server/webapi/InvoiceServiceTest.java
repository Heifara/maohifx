/**
 *
 */
package com.maohi.software.maohifx.server.webapi;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author heifara
 *
 */
public class InvoiceServiceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private File generatePDFFile(final InputStream aInputStream) {
		File iFile = null;
		try {
			iFile = File.createTempFile("temp", ".pdf");
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (OutputStream iOutputStream = new FileOutputStream(iFile)) {

			int iRead = 0;
			final byte[] iBytes = new byte[1024];

			while ((iRead = aInputStream.read(iBytes)) != -1) {
				iOutputStream.write(iBytes, 0, iRead);
			}

			iFile.createNewFile();

			return iFile;

		} catch (final FileNotFoundException aException) {
			// TODO Auto-generated catch block
			aException.printStackTrace();

			return null;
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldOkFromJSON() throws Exception {
		final Response iResponse = ClientBuilder.newClient().target("http://localhost:8080/maohifx.server/webapi/invoice/pdf").queryParam("uuid", "add89575-7e37-44ec-ab72-5b979a0ae037").request().get();
		assertTrue(Status.OK.equals(Status.fromStatusCode(iResponse.getStatus())));

		final File iFile = this.generatePDFFile(iResponse.readEntity(InputStream.class));
		System.out.println(iFile);

		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + iFile);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
