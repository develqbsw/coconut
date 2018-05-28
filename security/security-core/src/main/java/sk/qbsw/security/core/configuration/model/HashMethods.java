package sk.qbsw.security.core.configuration.model;

import org.apache.directory.api.ldap.model.constants.LdapSecurityConstants;

import sk.qbsw.core.base.exception.CSystemException;

/**
 * The hash method to hash data.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.8.0
 */
public enum HashMethods
{
	/**
	 * Md 5 hash methods.
	 */
	MD5 ("MD5", "MD5", LdapSecurityConstants.HASH_METHOD_MD5, "MD5"),

	/**
	 * Sha hash methods.
	 */
	SHA ("SHA", "SHA", LdapSecurityConstants.HASH_METHOD_SHA, "SHA-1"),

	/**
	 * Sha 256 hash methods.
	 */
	SHA256 ("SHA-256", "SHA-256", LdapSecurityConstants.HASH_METHOD_SHA256, "SHA-256"),

	/**
	 * Sha 512 hash methods.
	 */
	SHA512 ("SHA-512", "SHA-512", LdapSecurityConstants.HASH_METHOD_SHA512, null),

	/**
	 * Ssha 512 hash methods.
	 */
	SSHA512 ("SSHA-512", "SHA-512", LdapSecurityConstants.HASH_METHOD_SSHA512, null);

	private String name;

	private String algorithm;

	private LdapSecurityConstants ldapAlgorithm;

	private String databaseAlgorithm;

	HashMethods (String name, String algorithm, LdapSecurityConstants ldapAlgorithm, String databaseAlgorithm)
	{
		this.name = name;
		this.algorithm = algorithm;
		this.ldapAlgorithm = ldapAlgorithm;
		this.databaseAlgorithm = databaseAlgorithm;
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName ()
	{
		return name;
	}

	/**
	 * Gets algorithm.
	 *
	 * @return the algorithm
	 */
	public String getAlgorithm ()
	{
		return algorithm;
	}

	/**
	 * Gets ldap algorithm.
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
	 * Gets database algorithm.
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
