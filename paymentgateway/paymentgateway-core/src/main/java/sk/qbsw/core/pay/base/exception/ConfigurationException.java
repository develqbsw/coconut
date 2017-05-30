/**
 * 
 */
package sk.qbsw.core.pay.base.exception;

/**
 * Error in fongiguration of payment module. 
 * unsupported encoding, bad xml marshaller, etc.. 
 * @author martinkovic
 * @version 1.16.0
 * @since 1.16.0 
 *
 */
public class ConfigurationException extends RuntimeException
{

	/**
	 * @param e
	 */
	public ConfigurationException (Exception e)
	{
		super(e);
	}

}
