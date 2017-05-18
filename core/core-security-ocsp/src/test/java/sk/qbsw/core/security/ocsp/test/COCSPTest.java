package sk.qbsw.core.security.ocsp.test;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.InvalidParameterException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.commons.io.FileUtils;

import sk.qbsw.core.security.ocsp.service.COCSPCertValidatorBCImpl;
import sk.qbsw.core.security.ocsp.service.COCSPHttpURLConnection;
import sk.qbsw.core.security.ocsp.service.COCSPValidationResult;
import sk.qbsw.core.security.ocsp.service.IOCSPCertValidator;

/**
 * Test application for validating requests.
 * 
 * How to call this app: java sk.qbsw.core.security.test.ocsp.COCSPTest password V/E
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public class COCSPTest {

	/** The Constant baseCertsPath. */
	private final static String baseCertsPath = "/Users/rak/work/projects/OCSP-check/OCSP-check-1/certs/";

	/** The Constant baseUrlKeystore. */
	private final static String baseUrlKeystore = "/Users/rak/work/projects/OCSP-check/OCSP-check-1/keystores/";

	/** The Constant serviceAddr. */
	private final static String serviceAddr = "https://s2.ica.cz/cgi-bin/ocsp.cgi";

	/**
	 * Gets the certificate.
	 *
	 * @param path
	 *            the path
	 * @return the certificate
	 * @throws CertificateException
	 *             the certificate exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static X509Certificate getCertificate(String path) throws CertificateException, IOException {
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		X509Certificate retVal = null;
		FileInputStream is = null;
		try {
			is = new FileInputStream(path);
			retVal = (X509Certificate) certFactory.generateCertificate(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

		return retVal;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		//		X509Certificate interCert = getCertificate(baseCertsPath + "test-unknown.cer");
		//		X509Certificate interCert = getCertificate(baseCertsPath + "test-revoked.cer");
		X509Certificate interCert = getCertificate(baseCertsPath + "test-success.cer");
		//X509Certificate interCert = getCertificate(baseCertsPath + "wrong.cer");
		X509Certificate issuerCert = getCertificate(baseCertsPath + "ica.issuers.cer");

		IOCSPCertValidator validator = new COCSPCertValidatorBCImpl();
		byte[] ocspRequestData = validator.generateOCSPRequest(issuerCert, interCert);

		if (serviceAddr != null) {
			try {
				if (serviceAddr.startsWith("https")) {
					System.setProperty("javax.net.ssl.trustStore", baseUrlKeystore + "mytrusted.jks");
					System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
					System.setProperty("javax.net.ssl.keyStore", baseUrlKeystore + "myocsp2.jks");
					System.setProperty("javax.net.ssl.keyStorePassword", args[0]);
					System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
					System.setProperty("javax.net.debug", "none");
					System.setProperty("sun.net.www.protocol.http.HttpURLConnection.level", "ALL");
				}

				COCSPHttpURLConnection con = null;
				URL url = new URL((String) serviceAddr);

				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.121.31", 3128));
				con = new COCSPHttpURLConnection((HttpURLConnection) url.openConnection(proxy));

				OutputStream out = con.getOutputStream();
				DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(out));
				dataOut.write(ocspRequestData);
				dataOut.flush();
				dataOut.close();

				//for errors in response:
				if (con.getResponseCode() / 100 != 2) {
					throw new RuntimeException("HTTP Request exception occurred");
				}

				//Read the Response
				InputStream in = (InputStream) con.getContent();

				String mode = args.length > 1 ? args[1] : "wrong";
				switch (mode) {
					case "V":
						validateCertificate(validator, in);
						break;

					case "E":
						String exportFileName = args[2];
						exportResponse(in, exportFileName);
						break;

					default:
						throw new InvalidParameterException("Unknown mode selected");
				}

			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * Validate certificate.
	 *
	 * @param validator
	 *            the validator
	 * @param in
	 *            the in
	 */
	private static void validateCertificate(IOCSPCertValidator validator, InputStream in) {
		COCSPValidationResult validationResult = validator.processResponse(in);
		System.out.println("Call result : " + validationResult.getOcspStatus() + ":" + validationResult.getCertificateStatus());
	}

	/**
	 * Exports response.
	 *
	 * @param in
	 *            the in
	 * @param exportFileName
	 *            the export file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void exportResponse(InputStream in, String exportFileName) throws IOException {
		File targetFile = new File(exportFileName);
		FileUtils.copyInputStreamToFile(in, targetFile);
		System.out.println("Response exported to file " + exportFileName);
	}
}
