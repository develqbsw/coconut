/**
 * 
 */
package sk.qbsw.core.pay.base.reciept;

/**
 * 
 * @author martinkovic
 *
 */
public class PaymentRecieptImpl implements PaymentReciept
{

	private String paymentId;

	private String urlToCall;

	private boolean isGetCall;

	public PaymentRecieptImpl (String paymentId, String urlToCall, boolean isGetCall)
	{
		super();
		this.paymentId = paymentId;
		this.urlToCall = urlToCall;
		this.isGetCall = isGetCall; 
	}

	public PaymentRecieptImpl ()
	{
	}

	public String getPaymentId ()
	{
		return paymentId;
	}

	public void setPaymentId (String paymentId)
	{
		this.paymentId = paymentId;
	}

	public String getUrlToCall ()
	{
		return urlToCall;
	}

	public void setUrlToCall (String urlToCall)
	{
		this.urlToCall = urlToCall;
	}

	public boolean isGetCall ()
	{
		return isGetCall;
	}

	public void setGetCall (boolean isGetCall)
	{
		this.isGetCall = isGetCall;
	}

}
