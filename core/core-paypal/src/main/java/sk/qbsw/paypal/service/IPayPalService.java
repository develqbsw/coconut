package sk.qbsw.paypal.service;

import sk.qbsw.paypal.model.CPayPalPaymentData;

/**
 * The Interface IPayPalService.
 *
 * @author rosenberg
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPayPalService
{

	/**
	 * Sets the express checkout and return redirect address to PAYPAL.
	 * @param paypalServiceData input service data
	 * @return URL for redirect to PAYPAL
	 */
	public String getRedirectionToPaypalAfterSettingExpressCheckout (CPayPalPaymentData paypalPaymentData, String okURL, String errorURL);

	/**
	 * Do express checkout.
	 *
	 * @param token the token
	 * @param payerId the payer id
	 * @param license the license
	 * @return the nVP response
	 */
	public Boolean doExpressCheckoutResult (String token, String payerId, CPayPalPaymentData paypalPaymentData);
}
