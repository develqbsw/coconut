package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.base.exception.PasswordFormatException;

import java.time.OffsetDateTime;

/**
 * The user credential management service.
 *
 * @author Dalibor Rak
 * @version 1.18.4
 * @since 1.13.0
 */
public interface AccountCredentialManagementService
{
	/**
	 * Change encrypted password.
	 *
	 * @param login the login
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	void changeEncryptedPassword (String login, String password) throws CSecurityException;

	/**
	 * Change encrypted password.
	 *
	 * @param login the login
	 * @param currentPassword the current password
	 * @param newPassword the new password
	 * @throws CSecurityException the c security exception
	 */
	void changeEncryptedPassword (String login, String currentPassword, String newPassword) throws CSecurityException;

	/**
	 * Change encrypted password.
	 *
	 * @param login the login
	 * @param password the password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	void changeEncryptedPassword (String login, String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException;

	/**
	 * Change encrypted password.
	 *
	 * @param login the login
	 * @param currentPassword the current password
	 * @param newPassword the new password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	void changeEncryptedPassword (String login, String currentPassword, String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException;

	/**
	 * Change plain password.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	void changePlainPassword (String login, String email, String password) throws CSecurityException;

	/**
	 * Change plain password.
	 *
	 * @param login the login
	 * @param email the email
	 * @param currentPassword the current password
	 * @param newPassword the new password
	 * @throws CSecurityException the c security exception
	 */
	void changePlainPassword (String login, String email, String currentPassword, String newPassword) throws CSecurityException;

	/**
	 * Change plain password.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	void changePlainPassword (String login, String email, String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException;

	/**
	 * Change plain password.
	 *
	 * @param login the login
	 * @param email the email
	 * @param currentPassword the current password
	 * @param newPassword the new password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	void changePlainPassword (String login, String email, String currentPassword, String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException;

	/**
	 * Change login.
	 *
	 * @param userId the user id
	 * @param login the login
	 * @throws CSecurityException the c security exception
	 */
	void changeLogin (Long userId, String login) throws CSecurityException;

	/**
	 * Validate password.
	 *
	 * @param password the password
	 * @throws PasswordFormatException the password format exception
	 */
	void validatePassword (String password) throws PasswordFormatException;
}
