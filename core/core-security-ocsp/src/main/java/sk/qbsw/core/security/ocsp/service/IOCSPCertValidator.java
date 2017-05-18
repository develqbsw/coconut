package sk.qbsw.core.security.ocsp.service;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.X509Certificate;

/**
 * The Interface IOCSPCertValidator.
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public interface IOCSPCertValidator {

	/**
	 * Generate ocsp request.
	 *
	 * @param issuerCert
	 *            the issuer certificate
	 * @param checkCertificateSerialNumber
	 *            the check certificate serial number
	 * @return the byte[]
	 */
	public byte[] generateOCSPRequest(X509Certificate issuerCert, BigInteger checkCertificateSerialNumber);

	/**
	 * Generate ocsp request.
	 *
	 * @param issuerCert
	 *            the issuer certificate
	 * @param checkCertificate
	 *            certificate to check
	 * @return the byte[]
	 */
	public byte[] generateOCSPRequest(X509Certificate issuerCert, X509Certificate checkCertificate);

	/**
	 * Process response.
	 *
	 * @param ocspResponseStream
	 *            the ocsp response stream
	 * @return the COCSP validation result
	 */
	public COCSPValidationResult processResponse(InputStream ocspResponseStream);
}
