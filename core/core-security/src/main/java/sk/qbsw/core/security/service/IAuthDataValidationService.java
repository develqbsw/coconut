package sk.qbsw.core.security.service;

import java.io.Serializable;

import sk.qbsw.core.security.exception.CPasswordFormatException;

/**
 * Authentication data validation service.
 *
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.7.2
 */
interface IAuthDataValidationService extends Serializable
{
	/**
	 * Validate password format.
	 *
	 * @param password the password
	 * @throws CPasswordFormatException the password format exception
	 */
	public void validatePassword (String password) throws CPasswordFormatException;
}
