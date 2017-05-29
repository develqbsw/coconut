/**
 * 
 */
package sk.qbsw.core.pay.base;

/**
 * inteface popisuje spravanie pl. bran s pesistencnym frameworkom. 
 * 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentPersistence
{
	/**
	 * gets payment from paymentID computed from bank response. 
	 * 
	 * MUST BE NOT NULL
	 * 
	 * @param id
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0
	 */
	PaymentRealization getPaymentById(String id);

	/**
	 * @param payment
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	void update (PaymentRealization payment);

}
