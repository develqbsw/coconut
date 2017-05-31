package sk.qbsw.security.ocsp.test.validator;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sk.qbsw.security.ocsp.service.OCSPCertValidatorBCImpl;
import sk.qbsw.security.ocsp.service.OCSPValidationResult;
import sk.qbsw.security.ocsp.service.OCSPResponseStatus;
import sk.qbsw.security.ocsp.service.OCSPCertValidator;
import sun.security.provider.certpath.OCSP.RevocationStatus.CertStatus;

/**
 * Test for basic behavipur for OCSP implementatin from Bouncy castle
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public class ProcessResponseTestCase
{

	/** The validator. */
	private OCSPCertValidator validator;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp ()
	{
		validator = new OCSPCertValidatorBCImpl();
		Logger.getLogger(ProcessResponseTestCase.class.getName()).fine("Setup");
	}

	/**
	 * Test empty check certificate.
	 *
	 * @throws CertificateException
	 *             the certificate exception
	 */
	@Test
	public void testGetResponseSuccess () throws CertificateException
	{
		InputStream is = this.getClass().getResourceAsStream("/certs/ocsp-ok.resp");
		OCSPValidationResult result = validator.processResponse(is);

		// must be good
		Assert.assertTrue(result.getCertificateStatus().equals(CertStatus.GOOD));

		// must be successful
		Assert.assertTrue(result.getOcspStatus().equals(OCSPResponseStatus.SUCCESSFUL));
	}
}
