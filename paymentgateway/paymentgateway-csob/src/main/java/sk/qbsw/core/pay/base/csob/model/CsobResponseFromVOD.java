/**
 * 
 */
package sk.qbsw.core.pay.base.csob.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
@XmlRootElement (name = "responseFromVOD")
public class CsobResponseFromVOD
{
	private String channel="CSOB_PT";

	private String transactionId;

	private String status;

	private String errorMessage;

	public String getChannel ()
	{
		return channel;
	}

	@XmlElement (name = "Channel")
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
	@XmlElement (name = "Status")
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
	@XmlElement (name = "ErrorMessage")
	public void setErrorMessage (String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getTransactionId ()
	{
		return transactionId;
	}

	@XmlElement (name = "TransactionID")
	public void setTransactionId (String transactionId)
	{
		this.transactionId = transactionId;
	}
	
	public String toXMLString(){
		return "<responseFromVOD>" + 
			"<Channel>"+channel+"</Channel>" + 
			"<ErrorMessage>"+StringUtils.defaultIfBlank(errorMessage,"")+"</ErrorMessage>" + 
			"<Status>"+status+"</Status>" + 
			"<TransactionID>"+transactionId+"</TransactionID>" + 
			"</responseFromVOD>";
	}


}
