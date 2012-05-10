package sk.qbsw.paypal.domain.model;

import java.io.Serializable;

/**
 * Model for PAYPAL service.
 * @author rosenberg
 * @since 1.0.0
 * @version 1.0.0 
 */
public class CPayPalServiceData implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Object stores data for PAYPAL payment.
	 */
	private CPayPalPaymentData payment;

	public CPayPalPaymentData getPayment ()
	{
		return payment;
	}

	public void setPayment (CPayPalPaymentData payment)
	{
		this.payment = payment;
	}

}
