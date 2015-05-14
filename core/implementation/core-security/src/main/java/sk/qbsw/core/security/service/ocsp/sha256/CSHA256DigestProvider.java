package sk.qbsw.core.security.service.ocsp.sha256;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.DigestCalculator;

import sk.qbsw.core.base.exception.CSystemException;

/**
 * SHA256 implementation of Digest provider fromBoyncy Castle
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 * 
 * @see DigestCalculator
 */
public class CSHA256DigestProvider implements DigestCalculator {

	/** The stream. */
	private ByteArrayOutputStream stream;

	/* (non-Javadoc)
	 * @see org.bouncycastle.operator.DigestCalculator#getAlgorithmIdentifier()
	 */
	@Override
	public AlgorithmIdentifier getAlgorithmIdentifier() {
		return new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE);
	}

	/* (non-Javadoc)
	 * @see org.bouncycastle.operator.DigestCalculator#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		stream = new ByteArrayOutputStream();
		return stream;
	}

	/**
	 * See parent.
	 * 
	 * @see org.bouncycastle.operator.DigestCalculator#getDigest()
	 * @throws SecurityException
	 *             if no such algorithm or provider is available
	 */
	@Override
	public byte[] getDigest() {
		Security.addProvider(new BouncyCastleProvider());
		byte[] retVal = null;

		try {
			MessageDigest mda = MessageDigest.getInstance("SHA-256", "BC");
			retVal = mda.digest(stream.toByteArray());
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new CSystemException("Cannot generate digest.", e);
		}
		return retVal;
	}
}
