package sk.qbsw.core.base.test.encryption;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;

import org.junit.Test;

import sk.qbsw.core.base.encryption.CAESCryptoTool;

/**
 * The Class CAESCryptoToolTestCase.
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public class CAESCryptoToolTestCase {

	/** The plain. */
	private static String plain = "Hi, this is my message to test";

	/**
	 * Test encryption decryption.
	 *
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Test
	public void testEncryptionDecryption() throws UnsupportedEncodingException {
		CAESCryptoTool tool = new CAESCryptoTool();
		tool.init(CAESCryptoTool.generateKey("this is our key"));

		byte[] cipher = tool.encrypt(plain.getBytes());
		String decrypted = new String(tool.decrypt(cipher));

		assertTrue("Plain and decrypted are not same.", plain.equals(decrypted));
	}

	/**
	 * Test key generator for duplicity of generated key.
	 */
	@Test
	public void testKeyGenerator() {
		String key1 = CAESCryptoTool.generateKey("this is our key");
		String key2 = CAESCryptoTool.generateKey("this is our key");

		assertFalse("Generated keys are equals!", key1.equals(key2));
	}

	/**
	 * Test setting key by validating the plain and decrypted value.
	 *
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Test
	public void testSettingKey() throws UnsupportedEncodingException {
		byte[] key = "0123456789abcdef".getBytes();
		String plain = "TestMessage";

		CAESCryptoTool tool = new CAESCryptoTool();
		tool.init(key.clone());
		byte[] cipher = tool.encrypt(plain.getBytes());

		CAESCryptoTool tool2 = new CAESCryptoTool();
		tool2.init(key.clone());
		String decrypted = new String(tool.decrypt(cipher));
		assertTrue("Plain and decrypted value doesn't match!", decrypted.equals(plain));

	}

	/**
	 * Tests wrong input for encryption
	 *
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Test(expected = InvalidParameterException.class)
	public void testWrongInput() {
		byte[] key = "0123456789abcdef".getBytes();
		String plain = "";

		CAESCryptoTool tool = new CAESCryptoTool();
		tool.init(key.clone());
		byte[] cipher = tool.encrypt(plain.getBytes());

		CAESCryptoTool tool2 = new CAESCryptoTool();
		tool2.init(key.clone());
		String decrypted = new String(tool.decrypt(cipher));
		assertTrue("Plain and decrypted value doesn't match!", decrypted.equals(plain));

	}

	/**
	 * Tests null input for encryption
	 *
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@Test(expected = InvalidParameterException.class)
	public void testNullInput() {
		byte[] key = "0123456789abcdef".getBytes();

		CAESCryptoTool tool = new CAESCryptoTool();
		tool.init(key.clone());
		tool.encrypt(null);
	}
}
