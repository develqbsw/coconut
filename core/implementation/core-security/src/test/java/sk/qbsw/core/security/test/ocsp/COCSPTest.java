package sk.qbsw.core.security.test.ocsp;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import sk.qbsw.core.security.service.ocsp.COCSPCertValidatorBCImpl;
import sk.qbsw.core.security.service.ocsp.COCSPHttpURLConnection;
import sk.qbsw.core.security.service.ocsp.COCSPValidationResult;
import sk.qbsw.core.security.service.ocsp.IOCSPCertValidator;

public class COCSPTest {

	private final static String baseCertsPath = "/Users/rak/work/projects/OCSP-check/OCSP-check-1/certs/";

	private final static String baseUrlKeystore = "/Users/rak/work/projects/OCSP-check/OCSP-check-1/keystores/";

	private final static String serviceAddr = "https://s2.ica.cz/cgi-bin/ocsp.cgi";

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

	public static void main(String[] args) throws Exception {
		X509Certificate interCert = getCertificate(baseCertsPath + "ligazova.cer");
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
				con = new COCSPHttpURLConnection((HttpURLConnection) url.openConnection());

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
			
				COCSPValidationResult validationResult = validator.processResponse(in);
				System.out.println("Call result : " + validationResult.getOcspStatus() + ":" + validationResult.getCertificateStatus());

			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
