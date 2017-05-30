/**
 * 
 */
package sk.qbsw.core.pay.base;

/**
 * objekt pobsahuje informacie o zrealizovaní platby 
 * sem si jednotlivé platobné brány ukladajú informacie ktore potrebuju na spravne spracovanie platby. 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class PaymentRealization
{
	private String paymentId;
	/**
	 * URL to witch client should be redirected.
	 */
	private String urlToCall;
	
	//if true then the urlToCall contains GET Call
	//alese then the urlToCall contains POST Call
	private boolean isGetCall;
	
	/**
	 * textual representation of bank response
	 * some banks communicate in process of creation of payment request
	 */
	private String bankResponse;
	
	public static PaymentRealization of(String payId,String urlCall,boolean isGet){
		PaymentRealization realization = new PaymentRealization();
		realization.setGetCall(isGet);
		realization.setUrlToCall(urlCall);
		realization.setPaymentId(payId);
		return realization;
	}
	
	public String getPaymentId ()
	{
		return paymentId;
	}
	public void setPaymentId (String paymentId)
	{
		this.paymentId = paymentId;
	}
	public boolean isGetCall ()
	{
		return isGetCall;
	}
	public void setGetCall (boolean isGetCall)
	{
		this.isGetCall = isGetCall;
	}
	public String getUrlToCall ()
	{
		return urlToCall;
	}
	public void setUrlToCall (String urlToCall)
	{
		this.urlToCall = urlToCall;
	}
	public String getBankResponse ()
	{
		return bankResponse;
	}
	public void setBankResponse (String bankResponse)
	{
		this.bankResponse = bankResponse;
	}
	
}
