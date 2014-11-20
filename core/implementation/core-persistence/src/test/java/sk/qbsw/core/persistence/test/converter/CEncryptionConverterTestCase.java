package sk.qbsw.core.persistence.test.converter;

import org.junit.Assert;
import org.junit.Test;

import sk.qbsw.core.persistence.converter.CEncryptionConverter;

/**
 * Tests for DB EncryptionConverter
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public class CEncryptionConverterTestCase {

	/**
	 * Test for result of covnersion = converts to Db and converts back do java.
	 */
	@Test
	public void testForResult() {

		String plain = "p12";
		CEncryptionConverter converter = new CEncryptionConverter();

		String encrypted = converter.convertToDatabaseColumn(plain);
		String decrypted = converter.convertToEntityAttribute(encrypted);

		// check result
		Assert.assertTrue("Plain and decrypted are not equals!", decrypted.equals(plain));

		// check quality
		Assert.assertFalse("Encrypted and plain are the same", encrypted.equals(plain));
	}

}
