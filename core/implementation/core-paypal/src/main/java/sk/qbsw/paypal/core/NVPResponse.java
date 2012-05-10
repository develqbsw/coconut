/**
 * 
 */
package sk.qbsw.paypal.core;

import java.util.HashMap;

/**
 * The Class NVPResponse.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public class NVPResponse extends HashMap<String, String>
{
	/**
	 * Instantiates a new nVP response.
	 */
	public NVPResponse ()
	{
		super();
	}

	/**
	 * Instantiates a new nVP response.
	 *
	 * @param toCopy the to copy
	 */
	public NVPResponse (NVPResponse toCopy)
	{
		super(toCopy);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Occured error.
	 *
	 * @return true, if successful
	 */
	public boolean occuredError ()
	{
		return !"Success".equalsIgnoreCase(get("ACK"));
	}
}
