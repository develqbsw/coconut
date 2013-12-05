package sk.qbsw.android.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to work with crypting usually passwords
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CEncrypt
{
	/**
	 * crypt string to SHA2 hash encryption
	 * @param text to crypt
	 * @return crypted text
	 * @throws NoSuchAlgorithmException when algorithm doesn't exist
	 * @throws UnsupportedEncodingException when encoding doesn't exist
	 */
	public static String SHA2 (String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest mesd = MessageDigest.getInstance("SHA-256");
		byte[] bytes = text.getBytes("iso-8859-1");
		mesd.update(bytes, 0, bytes.length);
		return new String(mesd.digest());		
	}
	
	/**
	 * compare two SHA2 crypted strings 
	 * @param oldCrypted old crypted text 
	 * @param newUncrypted new uncrypted text
	 * @return true if is newCryptedText after hashing equals to oldCryptedText
	 * @throws NoSuchAlgorithmException when algorithm doesn't exist
	 * @throws UnsupportedEncodingException when encoding doesn't exist
	 */
	public static boolean compareSHA2 (String oldCrypted, String newUncrypted) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String newCrypted = SHA2(newUncrypted);
		return newCrypted.equals(oldCrypted);
	}
}
