package sk.qbsw.security.ocsp.service;

import sun.security.provider.certpath.OCSP.RevocationStatus.CertStatus;

/**
 * Result from OCSP validation. Contains status of call and status of certificate validation.
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 * 
 */
@SuppressWarnings("restriction")
public class COCSPValidationResult {

	/** The ocsp call status. */
	private EOCSPResponseStatus ocspStatus;

	/** The certificate status. */
	private CertStatus certificateStatus;

	/**
	 * Instantiates a new COCSP validation result.
	 */
	public COCSPValidationResult() {
		super();
	}

	/**
	 * Sets the ocsp status.
	 *
	 * @param ocspStatus
	 *            the new ocsp status
	 */
	public void setOcspStatus(EOCSPResponseStatus ocspStatus) {
		this.ocspStatus = ocspStatus;
	}

	/**
	 * Sets the certificate status.
	 *
	 * @param certificateStatus
	 *            the new certificate status
	 */
	public void setCertificateStatus(CertStatus certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	/**
	 * Gets the ocsp status.
	 *
	 * @return the ocsp status
	 */
	public EOCSPResponseStatus getOcspStatus() {
		return ocspStatus;
	}

	/**
	 * Gets the certificate status.
	 *
	 * @return the certificate status
	 */
	public CertStatus getCertificateStatus() {
		return certificateStatus;
	}

}
