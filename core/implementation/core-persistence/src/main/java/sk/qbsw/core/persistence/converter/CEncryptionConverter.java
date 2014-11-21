package sk.qbsw.core.persistence.converter;

import java.io.UnsupportedEncodingException;

import javax.persistence.AttributeConverter;

import org.apache.commons.codec.binary.Base64;

import sk.qbsw.core.base.encryption.CAESCryptoTool;
import sk.qbsw.core.base.encryption.IDecryptor;
import sk.qbsw.core.base.encryption.IEncryptor;
import sk.qbsw.core.base.exception.CSystemException;

/**
 * Converter for encrypting table column content
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public class CEncryptionConverter implements AttributeConverter<String, String> {

	/** The encryptor. */
	private IEncryptor<byte[], byte[]> encryptor;

	/** The decryptor. */
	private IDecryptor<byte[], byte[]> decryptor;

	/**
	 * Instantiates a new c encryption converter.
	 */
	public CEncryptionConverter() {
		CAESCryptoTool aesTool = new CAESCryptoTool();
		aesTool.init();
		this.encryptor = aesTool;
		this.decryptor = aesTool;
	}

	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public String convertToDatabaseColumn(String attribute) {
		if (attribute == null || attribute.length() == 0){
			return attribute;
		}

		try {
			return Base64.encodeBase64String(encryptor.encrypt(attribute.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new CSystemException("Cannot convert value for encryption.", e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public String convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.length() == 0){
			return dbData;
		}

		try {
			byte[] cipher = Base64.decodeBase64(dbData);
			return new String(decryptor.decrypt(cipher), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new CSystemException("Cannot convert encrypted value.", e);
		}
	}

}
