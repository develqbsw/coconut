package sk.qbsw.sgwt.winnetou.client.exception;

import sk.qbsw.sgwt.winnetou.client.ui.localization.CErrorConstants;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Authentication exception class.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CSecurityException extends Exception implements IsSerializable
{

	private static final long serialVersionUID = 1L;

	protected String message = new String();

	/**
	 * Constructor.
	 */
	public CSecurityException()
	{
		super();
	}

	/**
	 * Constructor with message text.
	 * 
	 * @param s
	 *            message text
	 */
	public CSecurityException(String s)
	{
		super(s, null);
		this.message = CErrorConstants.winnetouExceptionPrefix + s;
	}

	/**
	 * Constructor with message text and {@link Throwable} instance.
	 * 
	 * @param s
	 *            message text
	 * @param cause
	 *            {@link Throwable} instance
	 */
	public CSecurityException(String s, Throwable cause)
	{
		super(s, cause);
		this.message = CErrorConstants.winnetouExceptionPrefix + s;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return message;
	}

	public String getPlainMessage()
	{
		return this.message.substring(CErrorConstants.winnetouExceptionPrefix.length());
	}
}
