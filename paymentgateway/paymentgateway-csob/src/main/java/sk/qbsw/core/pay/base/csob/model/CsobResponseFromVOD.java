/**
 * 
 */
package sk.qbsw.core.pay.base.csob.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
@XmlRootElement (name = "responseFromVOD")
public class CsobResponseFromVOD
{
	@XmlElement (name = "Channel")
	private String channel="CSOB_PT";

	@XmlElement (name = "TransactionID")
	private String transactionId;

	@XmlElement (name = "Status")
	private String status;

	@XmlElement (name = "ErrorMessage")
	private String errorMessage;

	public String getChannel ()
	{
		return channel;
	}

	public void setChannel (String channel)
	{
		this.channel = channel;
	}

	

	/**
	 * @return the status
	 */
	public String getStatus ()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus (String status)
	{
		this.status = status;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage ()
	{
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage (String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getTransactionId ()
	{
		return transactionId;
	}

	public void setTransactionId (String transactionId)
	{
		this.transactionId = transactionId;
	}

}
