/**
 * 
 */
package sk.qbsw.core.pay.base;

/**
 * popisuje spravanie platby
 * success alebo FAIL
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public interface PaymentActions
{

	/**
	 * bank has signed response and it was canceled by authorized user on bank
	 * @param payment
	 * @param reason
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0
	 */
	void handleCancel (PaymentRealization payment);

	/**
	 * called on verifed and successed payment.
	 * bank is sure, this payment is OK , and payed
	 * @param payment
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0
	 */
	void handleSuccess (PaymentRealization payment);

	/**
	 * platba je správna, avšak banka ešte definitívne nedala potvrdenie o úspešnosti. (background procesy, hlavne SLSP) 
	 * @param payment
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	void handleUncertain (PaymentRealization payment);

	/**
	 * handle cancel from PaymentGate, where payment Id is not present, so it cannot pair payment. 
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0 
	 */
	void handleCancel ();

	/**
	 * handle fraud . 
	 * @author martinkovic
	 * @version 1.15.0
	 * @param paymentRealization 
	 * @since 1.15.0 
	 */
	void handleFraud (PaymentRealization paymentRealization);

}
