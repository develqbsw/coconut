package sk.qbsw.core.pay.base.csob.model;

/**
 * The Class SporoPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CsobEnvelope
{

	private String merchantId;
	private String message;

	public String createCompleteXML ()
	{
		String xml = "<signed>" +

			"<id>" + getMerchantId() + "</id>" +

			"<message>" + getMessage() + "</message>" +

			"</signed>";
		return xml;
	}

	public String getMerchantId ()
	{
		return merchantId;
	}

	public void setMerchantId (String merchantId)
	{
		this.merchantId = merchantId;
	}

	public String getMessage ()
	{
		return message;
	}

	public void setMessage (String message)
	{
		this.message = message;
	}

}
