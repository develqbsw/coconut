/**
 * 
 */
package sk.qbsw.core.pay.base.sporopay;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.PaymentRequest;
import sk.qbsw.core.pay.base.exception.DecryptionException;
import sk.qbsw.core.pay.base.exception.InvalidRequestException;
import sk.qbsw.core.pay.base.reciept.PaymentReciept;
import sk.qbsw.core.pay.base.reciept.PaymentRecieptImpl;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;
import sk.qbsw.dockie.core.gopay.model.CGopayCallback;
import sk.qbsw.dockie.core.gopay.model.CGopayItem;
import sk.qbsw.dockie.core.gopay.model.CGopayPayer;
import sk.qbsw.dockie.core.gopay.model.CGopaySimplePaymentRequest;
import sk.qbsw.dockie.core.gopay.model.CGopaySimplePaymentResponse;
import sk.qbsw.dockie.core.gopay.model.CGopayTarget;
import sk.qbsw.dockie.core.gopay.model.COAuthTokenResponse;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class GoPayPaymentProcessor extends PaymentProcessor
{

	private GoPayInitParams context;

	private RestTemplate restTemplate;


	/**
	 * @param context
	 */
	public GoPayPaymentProcessor (GoPayInitParams context)
	{
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#createPayment(sk.qbsw.dockie.core.payment.paymentProcessor.Payment)
	 */
	@Override
	public PaymentReciept createPayment (PaymentRequest payment)
	{ 
		String payid = payment.getIdentification().getPaymentId();

		HttpHeaders params = getOauthHeaders(context.getApiKey().get(), context.getApiSecret().get());

		CGopaySimplePaymentRequest request = new CGopaySimplePaymentRequest();
		CGopayPayer payer = new CGopayPayer();
		payer.setAllowed_payment_instruments(new String[] {"PAYMENT_CARD", "BANK_ACCOUNT", "PRSMS", "MPAYMENT", "PAYSAFECARD", "SUPERCASH", "GOPAY", "PAYPAL"});
		payer.setDefault_payment_instrument("PAYMENT_CARD");
		payer.setDefault_swift("TATRSKBX");
		payer.setAllowed_swifts(new String[] {"GIBACZPX", "RZBCCZPP", "BREXCZPP", "KOMBCZPP", "FIOBCZPP", "CEKOCZPP", "CEKOCZPP-ERA", "SUBASKBX", "TATRSKBX", "UNCRSKBX", "GIBASKBX", "OTPVSKBX", "POBNSKBA", "CEKOSKBX", "LUBASKBX"

		});
		request.setPayer(payer);
		request.setTarget(new CGopayTarget(context.getMerchantId()));
		request.setAmount(payment.getAmount().totalAmount().scaleByPowerOfTen(2).longValue());
		request.setCurrency("EUR");
		request.setOrder_number(payid);
		request.setOrder_description("");
		List<CGopayItem> items = new ArrayList<>();
		items.add(new CGopayItem("", payment.getAmount().totalAmount().scaleByPowerOfTen(2).longValue()));
		request.setItems(items);
		//		if (priceSummary.isRecurring())
		//		{
		//			CGopayRecurrence recurrence = new CGopayRecurrence();
		//			recurrence.setRecurrence_cycle(getPayParam("GOPAY.period"));
		//			recurrence.setRecurrence_period(1L);
		//			recurrence.setRecurrence_date_to(gopayDateFormat.print(new DateTime().plusMonths(priceSummary.getRecurringCount())));
		//
		//			request.setRecurrence(recurrence);
		//
		//		}

		request.setCallback(new CGopayCallback(context.getApplicationCallbackURLForBank() + "/confirmPayment/gopay?op=confirm&payment=" + payid, context.getApplicationCallbackURLForBank() + "/confirmPayment/gopay?op=changed&payment=" + payid));
		request.setLang("SK");

		CGopaySimplePaymentResponse response = createSimplepayment(request, params);

		String idpaymentGopay = response.getId().toString();

		response.getGw_url();

		PaymentReciept payments = new PaymentRecieptImpl(idpaymentGopay,response.getGw_url(),true);

		return payments;
	}

	/* (non-Javadoc)O
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#handleBankPaymentResponse(sk.qbsw.dockie.core.payment.paymentProcessor.BankResponse)
	 */
	@Override
	public PaymentRealization handleBankPaymentResponse (AbstractBankResponse resp)
	{
		BankResponse response = (BankResponse) resp;

		String myPaymentId = response.get("payment");
		String id = response.get("id");

		//check if payment exist
		PaymentRealization payments = getPersistence().getPaymentById(id);
		if (payments == null)
		{
			throw new InvalidRequestException("Payment not found");
		}

		CGopaySimplePaymentResponse payment = getFromGopayByToken(id);


		if (!payments.getPaymentId().toString().equals(myPaymentId))
		{
			throw new DecryptionException("IDs didnt match. Whoever sent this request, didnt know Payment ID");
		}

		if (isApproved(payment))
		{
			getActions().handleSuccess(payments);
		}
		else
		{
			getActions().handleCancel(payments);
		}

		String json = mapToJson(payment);
		payments.setBankResponse(json);
		getPersistence().update(payments);
		return payments;
	}

	
	public static Gson GSON = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	private String mapToJson (CGopaySimplePaymentResponse payment)
	{
		return GSON.toJson(payment);
	}

	public HttpHeaders getOauthHeaders (String username, String password)
	{

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);

		String request = "grant_type=client_credentials&scope=payment-all";
		HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);

		ResponseEntity<COAuthTokenResponse> responseEntity = restTemplate.exchange(context.getGopayGateURL() + "/oauth2/token", HttpMethod.POST, requestEntity, COAuthTokenResponse.class);

		HttpHeaders headersForCall = new HttpHeaders();
		headersForCall.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headersForCall.setContentType(MediaType.APPLICATION_JSON);
		headersForCall.set("Authorization", "Bearer " + responseEntity.getBody().getAccess_token());
		return headersForCall;
	}

	public CGopaySimplePaymentResponse createSimplepayment (CGopaySimplePaymentRequest request, HttpHeaders params)
	{
		HttpEntity<CGopaySimplePaymentRequest> requestEntity = new HttpEntity<>(request, params);

		ResponseEntity<CGopaySimplePaymentResponse> responseEntity = restTemplate.exchange(context.getGopayGateURL() + "/payments/payment", HttpMethod.POST, requestEntity, CGopaySimplePaymentResponse.class);

		return responseEntity.getBody();

	}

	public CGopaySimplePaymentResponse check (HttpHeaders params, String id)
	{
		params.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<Object> requestEntity = new HttpEntity<>(null, params);

		ResponseEntity<CGopaySimplePaymentResponse> responseEntity = restTemplate.exchange(context.getGopayGateURL() + "/payments/payment/" + id, HttpMethod.GET, requestEntity, CGopaySimplePaymentResponse.class);

		return responseEntity.getBody();
	}


	private boolean isApproved (CGopaySimplePaymentResponse payment)
	{
		if ("PAID".equalsIgnoreCase(payment.getState()))
		{
			//if is true
			return true;
		}
		else
		{
			return false;

		}
	}

	private CGopaySimplePaymentResponse getFromGopayByToken (String id)
	{
		HttpHeaders params = getOauthHeaders(context.getApiKey().get(), context.getApiSecret().get());

		CGopaySimplePaymentResponse payment = check(params, id);
		return payment;
	}

	
}
