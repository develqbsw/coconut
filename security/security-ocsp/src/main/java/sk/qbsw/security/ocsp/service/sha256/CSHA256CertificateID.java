package sk.qbsw.security.ocsp.service.sha256;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.OCSPException;

/**
 * SHA256 implementation of Certificate ID.
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public class CSHA256CertificateID extends CertificateID {

	/**
	 * Instantiates a new SHA256 certificate id.
	 *
	 * @param issuerCert
	 *            the issuer cert
	 * @param number
	 *            the number
	 * @throws OCSPException
	 *             the OCSP exception
	 * @throws CertificateEncodingException
	 *             the certificate encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 * @see CertificateID
	 */
	public CSHA256CertificateID(X509Certificate issuerCert, BigInteger number) throws OCSPException, CertificateEncodingException, IOException {
		super(new CSHA256DigestProvider(), new X509CertificateHolder(issuerCert.getEncoded()), number);
	}
}
