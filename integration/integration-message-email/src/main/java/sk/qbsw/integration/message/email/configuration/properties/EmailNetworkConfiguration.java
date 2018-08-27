package sk.qbsw.integration.message.email.configuration.properties;

/**
 * The email network configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface EmailNetworkConfiguration
{
	/**
	 * Gets host.
	 *
	 * @return the host
	 */
	String getHost ();

	/**
	 * Gets port.
	 *
	 * @return the port
	 */
	Integer getPort ();

	/**
	 * Gets transport protocol.
	 *
	 * @return the transport protocol
	 */
	String getTransportProtocol ();

	/**
	 * Gets smtp auth.
	 *
	 * @return the smtp auth
	 */
	String getSmtpAuth ();

	/**
	 * Gets smtp starttls enable.
	 *
	 * @return the smtp starttls enable
	 */
	String getSmtpStarttlsEnable ();

	/**
	 * Gets debug.
	 *
	 * @return the debug
	 */
	String getDebug ();

	/**
	 * Gets smtp connectiontimeout.
	 *
	 * @return the smtp connectiontimeout
	 */
	String getSmtpConnectiontimeout ();

	/**
	 * Gets smtp sendpartial.
	 *
	 * @return the smtp sendpartial
	 */
	String getSmtpSendpartial ();

	/**
	 * Gets smtp userset.
	 *
	 * @return the smtp userset
	 */
	String getSmtpUserset ();

	/**
	 * Gets mime charset.
	 *
	 * @return the mime charset
	 */
	String getMimeCharset ();

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	String getPassword ();

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	String getUsername ();
}
