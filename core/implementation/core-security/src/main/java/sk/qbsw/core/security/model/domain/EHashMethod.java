package sk.qbsw.core.security.model.domain;

import org.apache.directory.api.ldap.model.constants.LdapSecurityConstants;

import sk.qbsw.core.base.exception.CSystemException;

/**
 * The hash method to hash data.
 * 
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.8.0
 */
public enum EHashMethod
{
	/**  The MD5 encryption method. */
	MD5 ("MD5", "MD5", LdapSecurityConstants.HASH_METHOD_MD5, "MD5"),

	/**  The SHA encryption method. */
	SHA ("SHA", "SHA", LdapSecurityConstants.HASH_METHOD_SHA, "SHA-1"),

	/**  The SHA-256 encryption method. */
	SHA256 ("SHA-256", "SHA-256", LdapSecurityConstants.HASH_METHOD_SHA256, "SHA-256"),

	/**  The SHA-512 encryption method. */
	SHA512 ("SHA-512", "SHA-512", LdapSecurityConstants.HASH_METHOD_SHA512, null),

	/**  The salted SHA-512 encryption method. */
	SSHA512 ("SSHA-512", "SHA-512", LdapSecurityConstants.HASH_METHOD_SSHA512, null);

	/**  The associated name. */
	private String name;

	/**  The associated algorithm. */
	private String algorithm;

	/**  The associated LDAP algorithm. */
	private LdapSecurityConstants ldapAlgorithm;

	/**  The associated database algorithm. */
	private String databaseAlgorithm;

	/**
	 * Instantiates a new e hash method.
	 *
	 * @param name the name
	 * @param algorithm the algorithm
	 * @param ldapAlgorithm the ldap algorithm
	 * @param databaseAlgorithm the database algorithm
	 */
	private EHashMethod (String name, String algorithm, LdapSecurityConstants ldapAlgorithm, String databaseAlgorithm)
	{
		this.name = name;
		this.algorithm = algorithm;
		this.ldapAlgorithm = ldapAlgorithm;
		this.databaseAlgorithm = databaseAlgorithm;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Gets the algorithm.
	 *
	 * @return the algorithm
	 */
	public String getAlgorithm ()
	{
		return algorithm;
	}

	/**
	 * Gets the ldap algorithm.
	 *
	 * @return the ldap algorithm
	 */
	public LdapSecurityConstants getLdapAlgorithm ()
	{
		if (ldapAlgorithm == null)
		{
			throw new CSystemException("The usage of algorithm is not supported in LDAP");
		}
		else
		{
			return ldapAlgorithm;
		}
	}

	/**
	 * Gets the database algorithm.
	 *
	 * @return the database algorithm
	 */
	public String getDatabaseAlgorithm ()
	{
		if (ldapAlgorithm == null)
		{
			throw new CSystemException("The usage of algorithm is not supported in database");
		}
		else
		{
			return databaseAlgorithm;
		}
	}
}
