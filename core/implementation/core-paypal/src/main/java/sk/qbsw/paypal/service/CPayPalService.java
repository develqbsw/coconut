package sk.qbsw.paypal.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.paypal.core.NVPResponse;
import sk.qbsw.paypal.core.PayPal;
import sk.qbsw.paypal.domain.model.CPayPalPaymentData;
import sk.qbsw.paypal.domain.model.CPayPalServiceData;
import sk.qbsw.paypal.domain.model.CPaypalSettings;
import sk.qbsw.paypal.domain.model.CReleaseEnvironment;
import sk.qbsw.paypal.fields.Currency;
import sk.qbsw.paypal.fields.Payment;
import sk.qbsw.paypal.fields.PaymentAction;
import sk.qbsw.paypal.fields.PaymentItem;
import sk.qbsw.paypal.profile.BaseProfile;
import sk.qbsw.paypal.profile.Profile;
import sk.qbsw.paypal.request.DoExpressCheckoutPayment;
import sk.qbsw.paypal.request.SetExpressCheckout;

/**
 * Service used paypal interface 
 * Notes: paypal parameters: https://cms.paypal.com/us/cgi-bin/?cmd=_render-content&content_ID=developer/e_howto_api_nvp_r_SetExpressCheckout
 * @author rosenberg
 * @since 1.0.0
 * @version 1.0.0
 */
@Service ("CPayPalService")
public class CPayPalService implements IPayPalService
{

	private static final Logger LOGGER = Logger.getLogger(CPayPalService.class);

	@Autowired
	CPaypalSettings settings;

	@Autowired
	CReleaseEnvironment paypalReleaseEnvironment;

	public String getRedirectionToPaypalAfterSettingExpressCheckout (CPayPalServiceData paypalServiceData)
	{
		NVPResponse response = setExpressCheckout(paypalServiceData);
		return prepareRedirectToPaypal(paypalServiceData, response);
	}

	public Boolean doExpressCheckoutResult (String token, String payerId, CPayPalServiceData paypalServiceData)
	{
		NVPResponse prepareResponse = doExpressCheckout(token, payerId, paypalServiceData);
		return !prepareResponse.occuredError();
	}

	/**
	 * Initiate an Express Checkout transaction.
	 * @param paymentData input PAYPAL data
	 * @return response object
	 */
	@Transactional
	public NVPResponse setExpressCheckout (CPayPalServiceData paymentServiceData)
	{
		Profile user = new BaseProfile.Builder(settings.getUsername(), settings.getPassword()).signature(settings.getSignature()).build();

		PayPal pp = new PayPal(user, settings.getMode());
		pp.setProxy(paypalReleaseEnvironment.getProxyUrl(), paypalReleaseEnvironment.getProxyPort());

		PaymentItem[] items = new PaymentItem[1];
		items[0] = preparePaymentItem(paymentServiceData.getPayment());

		Payment payment = new Payment(items);
		payment.setCurrency(Currency.EUR);

		SetExpressCheckout setEC = new SetExpressCheckout(payment, settings.getOkURL(), settings.getErrorURL());
		setEC.setNoShipping(true);

		pp.setResponse(setEC);

		NVPResponse response = setEC.getNVPResponse();

		if (response.occuredError())
		{
			LOGGER.error("paying :" + setEC);
		}
		else
		{
			LOGGER.info("paying :" + setEC);
		}

		return response;
	}

	/**
	 * Prepare conditions to redirect to PAYPAL web and return URL for redirect.
	 * @param paymentServiceData input PAYPAL data
	 * @param prepareResponse response object
	 * @return URL as string
	 */
	@Transactional
	public String prepareRedirectToPaypal (CPayPalServiceData paymentServiceData, NVPResponse prepareResponse)
	{
		Profile user = new BaseProfile.Builder(settings.getUsername(), settings.getPassword()).signature(settings.getSignature()).build();
		PayPal pp = new PayPal(user, settings.getMode());
		pp.setProxy(paypalReleaseEnvironment.getProxyUrl(), paypalReleaseEnvironment.getProxyPort());

		String redirectURL = pp.getRedirectUrl(prepareResponse);
		return redirectURL;
	}

	/**
	 * After payment by PayPal do the check, if payment was realized. 
	 * @param token input token
	 * @param payerId input payer identifier
	 * @param paymentServiceData input payment data
	 * @return response object
	 */
	@Transactional
	public NVPResponse doExpressCheckout (String token, String payerId, CPayPalServiceData paymentServiceData)
	{
		Profile user = new BaseProfile.Builder(settings.getUsername(), settings.getPassword()).signature(settings.getSignature()).build();
		PayPal pp = new PayPal(user, settings.getMode());
		pp.setProxy(paypalReleaseEnvironment.getProxyUrl(), paypalReleaseEnvironment.getProxyPort());

		PaymentItem[] items = new PaymentItem[1];
		PaymentItem item = preparePaymentItem(paymentServiceData.getPayment());
		items[0] = item;

		Payment payment = new Payment(items);
		payment.setCurrency(Currency.EUR);

		DoExpressCheckoutPayment doExpressCheckoutPayment = new DoExpressCheckoutPayment(payment, token, PaymentAction.SALE, payerId);

		pp.setResponse(doExpressCheckoutPayment);
		NVPResponse response = doExpressCheckoutPayment.getNVPResponse();

		if (response.occuredError())
		{
			Logger.getLogger(getClass()).error("payment confirmation :" + doExpressCheckoutPayment);
		}
		else
		{
			Logger.getLogger(getClass()).info("payment confirmation :" + doExpressCheckoutPayment);
		}


		return response;
	}



	/**
	 * Prepare payment item.
	 *
	 * @param license the license
	 * @return the payment item
	 */
	private PaymentItem preparePaymentItem (CPayPalPaymentData paymentData)
	{
		PaymentItem item = new PaymentItem();

		item.setAmount(paymentData.getAmount());
		item.setDescription(paymentData.getNotes());
		item.setItemNumber(paymentData.getItemNumber());
		item.setQuantity(1);
		item.setTaxAmount(paymentData.getTaxAmount());

		return item;
	}
}
