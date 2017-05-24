package sk.qbsw.security.ocsp.test.validator;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import sk.qbsw.security.ocsp.service.COCSPCertValidatorBCImpl;
import sk.qbsw.security.ocsp.service.IOCSPCertValidator;

/**
 * Test for basic behavipur for OCSP implementatin from Bouncy castle
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public class COCSPGenerateRequestTestCase {

	/** The validator. */
	private IOCSPCertValidator validator;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		validator = new COCSPCertValidatorBCImpl();
		Logger.getLogger(COCSPGenerateRequestTestCase.class.getName()).fine("Setup");
	}

	/**
	 * Test empty issuer.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateRequestEmptyIssuer() {
		// expects failure
		validator.generateOCSPRequest((X509Certificate) null, (X509Certificate) null);
	}

	/**
	 * Test empty check certificate.
	 *
	 * @throws CertificateException
	 *             the certificate exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateRequestEmptyCheckCertificate() throws CertificateException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream certStream = this.getClass().getResourceAsStream("/certs/wrong.cer");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(certStream);

		// expects failure
		validator.generateOCSPRequest((X509Certificate) cert, (X509Certificate) null);
	}
}
