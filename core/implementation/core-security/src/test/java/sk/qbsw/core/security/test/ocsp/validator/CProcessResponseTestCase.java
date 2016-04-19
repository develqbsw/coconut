package sk.qbsw.core.security.test.ocsp.validator;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sk.qbsw.core.security.service.ocsp.COCSPCertValidatorBCImpl;
import sk.qbsw.core.security.service.ocsp.COCSPValidationResult;
import sk.qbsw.core.security.service.ocsp.EOCSPResponseStatus;
import sk.qbsw.core.security.service.ocsp.IOCSPCertValidator;
import sun.security.provider.certpath.OCSP.RevocationStatus.CertStatus;

/**
 * Test for basic behavipur for OCSP implementatin from Bouncy castle
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public class CProcessResponseTestCase
{

	/** The validator. */
	private IOCSPCertValidator validator;

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp ()
	{
		validator = new COCSPCertValidatorBCImpl();
		Logger.getLogger(CProcessResponseTestCase.class.getName()).fine("Setup");
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
		COCSPValidationResult result = validator.processResponse(is);

		// must be good
		Assert.assertTrue(result.getCertificateStatus().equals(CertStatus.GOOD));

		// must be successful
		Assert.assertTrue(result.getOcspStatus().equals(EOCSPResponseStatus.SUCCESSFUL));
	}
}
