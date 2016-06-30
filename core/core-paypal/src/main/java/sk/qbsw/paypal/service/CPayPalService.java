package sk.qbsw.paypal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.paypal.core.NVPResponse;
import sk.qbsw.paypal.core.PayPal;
import sk.qbsw.paypal.fields.Currency;
import sk.qbsw.paypal.fields.Payment;
import sk.qbsw.paypal.fields.PaymentAction;
import sk.qbsw.paypal.fields.PaymentItem;
import sk.qbsw.paypal.model.CPayPalPaymentData;
import sk.qbsw.paypal.model.CPaypalSettings;
import sk.qbsw.paypal.model.CReleaseEnvironment;
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
public class CPayPalService extends AService implements IPayPalService
{

	private static final Logger LOGGER = LoggerFactory.getLogger(CPayPalService.class);

	private CPaypalSettings settings;

	private CReleaseEnvironment paypalReleaseEnvironment;

	public String getRedirectionToPaypalAfterSettingExpressCheckout (CPayPalPaymentData paypalPaymentData, String okURL, String errorURL)
	{
		NVPResponse response = setExpressCheckout(paypalPaymentData, okURL, errorURL);
		return prepareRedirectToPaypal(paypalPaymentData, response);
	}

	public Boolean doExpressCheckoutResult (String token, String payerId, CPayPalPaymentData paypalPaymentData)
	{
		NVPResponse prepareResponse = doExpressCheckout(token, payerId, paypalPaymentData);
		return !prepareResponse.occuredError();
	}

	/**
	 * Initiate an Express Checkout transaction.
	 * @param paymentData input PAYPAL data
	 * @return response object
	 */
	@Transactional
	public NVPResponse setExpressCheckout (CPayPalPaymentData paypalPaymentData, String okURL, String errorURL)
	{
		Profile user = new BaseProfile.Builder(settings.getUsername(), settings.getPassword()).signature(settings.getSignature()).build();

		PayPal pp = new PayPal(user, settings.getMode());
		pp.setProxy(paypalReleaseEnvironment.getProxyUrl(), paypalReleaseEnvironment.getProxyPort());

		PaymentItem[] items = new PaymentItem[1];
		items[0] = preparePaymentItem(paypalPaymentData);

		Payment payment = new Payment(items);
		payment.setCurrency(Currency.EUR);

		SetExpressCheckout setEC = new SetExpressCheckout(payment, okURL, errorURL);
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
	public String prepareRedirectToPaypal (CPayPalPaymentData paypalPaymentData, NVPResponse prepareResponse)
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
	public NVPResponse doExpressCheckout (String token, String payerId, CPayPalPaymentData paypalPaymentData)
	{
		Profile user = new BaseProfile.Builder(settings.getUsername(), settings.getPassword()).signature(settings.getSignature()).build();
		PayPal pp = new PayPal(user, settings.getMode());
		pp.setProxy(paypalReleaseEnvironment.getProxyUrl(), paypalReleaseEnvironment.getProxyPort());

		PaymentItem[] items = new PaymentItem[1];
		PaymentItem item = preparePaymentItem(paypalPaymentData);
		items[0] = item;

		Payment payment = new Payment(items);
		payment.setCurrency(Currency.EUR);

		DoExpressCheckoutPayment doExpressCheckoutPayment = new DoExpressCheckoutPayment(payment, token, PaymentAction.SALE, payerId);

		pp.setResponse(doExpressCheckoutPayment);
		NVPResponse response = doExpressCheckoutPayment.getNVPResponse();

		if (response.occuredError())
		{
			LOGGER.error("payment confirmation :" + doExpressCheckoutPayment);
		}
		else
		{
			LOGGER.info("payment confirmation :" + doExpressCheckoutPayment);
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

	public CReleaseEnvironment getPaypalReleaseEnvironment ()
	{
		return paypalReleaseEnvironment;
	}

	public void setPaypalReleaseEnvironment (CReleaseEnvironment paypalReleaseEnvironment)
	{
		this.paypalReleaseEnvironment = paypalReleaseEnvironment;
	}

	public CPaypalSettings getSettings ()
	{
		return settings;
	}

	public void setSettings (CPaypalSettings settings)
	{
		this.settings = settings;
	}

}
