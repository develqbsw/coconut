package sk.qbsw.integration.ftp.file;

/**
 * The Interface IFileExchangerAuthenticator.
 *
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public interface IFileExchangerAuthenticator
{

	/**
	 * Sets the simple authentication.
	 *
	 * @param login the login
	 * @param password the password
	 */
	public void setSimpleAuthentication (String login, String password);

	/**
	 * Sets the certificate authentication.
	 *
	 * @param pathTocertificate the new certificate authentication
	 */
	public void setCertificateAuthentication (String pathTocertificate);
}