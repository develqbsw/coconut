package sk.qbsw.security.ocsp.service;

/**
 * Enumeration for OCSP respones. Complies with RFC specification
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public enum OCSPResponseStatus {
	// Response has valid confirmations
	/** The successful. */
	SUCCESSFUL(0),
	// Illegal confirmation request
	/** The malformedrequest. */
	MALFORMEDREQUEST(1),
	// Internal error in issuer
	/** The internalerror. */
	INTERNALERROR(2),
	// Try again later, (4) is not used
	/** The trylater. */
	TRYLATER(3),
	// Must sign the request
	/** The sigrequired. */
	SIGREQUIRED(5),
	// Request unauthorized
	/** The unauthorized. */
	UNAUTHORIZED(6);

	/** The status code. */
	private final int status;

	/**
	 * Instantiates a new EOCSP response status.
	 *
	 * @param status
	 *            the status
	 */
	private OCSPResponseStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Value of integer representation.
	 *
	 * @param status
	 *            the status
	 * @return the EOCSP response status
	 */
	public static OCSPResponseStatus valueOf(int status) {
		for (OCSPResponseStatus value : values()) {
			if (value.getStatus() == status) {
				return value;
			}
		}

		return null;
	}
}
