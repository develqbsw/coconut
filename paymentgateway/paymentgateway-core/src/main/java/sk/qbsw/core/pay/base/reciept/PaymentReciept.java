/**
 * 
 */
package sk.qbsw.core.pay.base.reciept;

/**
 * objekt pobsahuje informacie o zrealizovaní platby 
 * sem si jednotlivé platobné brány ukladajú informacie ktore potrebuju na spravne spracovanie platby. 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentReciept
{
	String getPaymentId();

	
	/**
	 * URL to witch client should be redirected.
	 */
	String getUrlToCall();
	
	//if true then the urlToCall contains GET Call
	//alese then the urlToCall contains POST Call
	boolean isGetCall();
	
	//if changed use instead of this methods 
	//HttpRequest impl. it contains URI and parameters with Http method
	
	
}
