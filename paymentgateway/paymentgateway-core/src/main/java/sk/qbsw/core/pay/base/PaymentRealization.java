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
	 * textual representation of bank response
	 * some banks communicate in process of creation of payment request
	 */
	private String bankResponse;
	
	public static PaymentRealization of(String payId){
		PaymentRealization realization = new PaymentRealization();
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
	
	public String getBankResponse ()
	{
		return bankResponse;
	}
	public void setBankResponse (String bankResponse)
	{
		this.bankResponse = bankResponse;
	}

	
}
