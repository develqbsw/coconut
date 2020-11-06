package sk.qbsw.core.base.encryption;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * Tool for encryption and decryption using AES algorithm.
 *
 * @author Dalibor Rak
 * @version 2.6.0
 * @since 1.12.0
 */
public class CAESCryptoTool implements IEncryptor<byte[], byte[]>, IDecryptor<byte[], byte[]>
{

	/** The Constant CIPHER_TYPE. */
	private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding";

	/** The Constant CIPHER_PROVIDER. */
	private static final String CIPHER_PROVIDER = "SunJCE";

	/** The Constant CIPHER_ALG. */
	private static final String CIPHER_ALG = "AES";

	/** The Constant ENCODING. */
	private static final String ENCODING = "UTF-8";

	/** The iv. */
	static String IV = "ABCDEFGHIJKLMDST";

	/** The encryption key. */
	private byte[] encryptionKey = "0123456789abcdef".getBytes();

	/**
	 * Inits the tool with key encoded in Base64.
	 *
	 * @param key the key in Base64 encoding
	 */
	public void init (String key)
	{
		init(Base64.decodeBase64(key));
	}

	/**
	 * Inits the tool with key represented in bytes.
	 *
	 * @param key key to use
	 */
	public void init (byte[] key)
	{
		this.encryptionKey = key;
	}

	/**
	 * Inits the encryption tool using CAESCryptoTool.key value.
	 */
	public void init ()
	{
		String keyFromSystem = System.getProperty("CAESCryptoTool.key");
		if (keyFromSystem != null && keyFromSystem.length() > 0)
		{
			this.init(keyFromSystem);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.base.encryption.IDecryptor#decrypt(java.lang.Object)
	 */
	@Override
	public byte[] decrypt (byte[] cipherData) throws CSecurityException
	{
		if (cipherData == null || cipherData.length == 0)
		{
			throw new InvalidParameterException("CipherData cannot be null");
		}
		try
		{
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
			return cipher.doFinal(cipherData);
		}
		catch (Exception ex)
		{
			throw new CSecurityException("Cannot decrypt data", ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.base.encryption.IEncryptor#encrypt(java.lang.Object)
	 */
	@Override
	public byte[] encrypt (byte[] plain) throws CSecurityException
	{
		if (plain == null || plain.length == 0)
		{
			throw new InvalidParameterException("Plain cannot be null");
		}

		try
		{
			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
			return cipher.doFinal(plain);
		}
		catch (Exception ex)
		{
			throw new CSecurityException("Cannot encrypt data", ex);
		}
	}

	/**
	 * Gets the cipher.
	 *
	 * @param mode the mode
	 * @return the cipher
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws NoSuchProviderException the no such provider exception
	 * @throws NoSuchPaddingException the no such padding exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws InvalidKeyException the invalid key exception
	 * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
	 */
	private synchronized Cipher getCipher (int mode) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException
	{
		Cipher cipher = Cipher.getInstance(CIPHER_TYPE, CIPHER_PROVIDER);
		SecretKeySpec key = new SecretKeySpec(encryptionKey, CIPHER_ALG);
		cipher.init(mode, key, new IvParameterSpec(IV.getBytes(ENCODING)));
		return cipher;
	}

	/**
	 * Generate key.
	 *
	 * @param keyPart keyPart for generating message
	 * @return the key encoded in Base64
	 */
	public static String generateKey (String keyPart) throws CSecurityException
	{
		try
		{
			if (keyPart == null || keyPart.length() < 5)
			{
				throw new InvalidParameterException("Key part is too short");
			}

			String salt = RandomStringUtils.random(20);
			byte[] key = (salt + keyPart).getBytes(ENCODING);

			// use SHA1 for hash generation
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			return Base64.encodeBase64String(key);
		}
		catch (Exception ex)
		{
			throw new CSecurityException("Cannot encrypt data", ex);
		}
	}
}
