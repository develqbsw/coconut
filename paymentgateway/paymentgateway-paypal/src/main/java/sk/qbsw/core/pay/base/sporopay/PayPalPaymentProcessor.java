/**
 * 
 */
package sk.qbsw.core.pay.base.sporopay;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PaymentDefinition;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.RelatedResources;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import sk.qbsw.core.pay.base.Payment;
import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.base.exception.ConfigurationException;
import sk.qbsw.core.pay.base.exception.DecryptionException;
import sk.qbsw.core.pay.base.exception.InvalidRequestException;
import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class PayPalPaymentProcessor extends PaymentProcessor
{


	private PayPalInitParams ctx;

	/**
	 * @param context
	 */
	public PayPalPaymentProcessor (PayPalInitParams context)
	{
		this.ctx = context;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.dockie.core.payment.paymentProcessor.PaymentProcessor#createPayment(sk.qbsw.dockie.core.payment.paymentProcessor.Payment)
	 */
	@Override
	public PaymentRealization createPayment (Payment payment)
	{
		PaymentRealization payments = new PaymentRealization();
		String payid = payment.suggestPayId();
		payments.setPaymentId(payid);

		//		if (!priceSummary.isRecurring())
		//		{
		List<Transaction> transactions = new ArrayList<>();

		createTransaction(transactions, payment);

		try
		{
			APIContext context = getContext(ctx.getApiKey().get(), ctx.getApiSecret().get());

			com.paypal.api.payments.Payment createPayment = createPayment(context, transactions, ctx.getApplicationCallbackURLForBank() + "/confirmPayment/paypal?op=confirm&payment=" + payid, ctx.getApplicationCallbackURLForBank() + "/confirmPayment/paypal?op=deny&payment=" + payid);
			payments.setUrlToCall(fetchApprovalUrl(createPayment.getLinks()).getHref());
			payments.setGetCall(true);

		}
		catch (PayPalRESTException e)
		{
			throw new ConfigurationException(e);
		}

		//		}
		//		else
		//		{
		//			payments.setMode(EPaymentMode.RECURRING);
		//			List<PaymentDefinition> defs = new ArrayList<>();
		//
		//			for (CPriceItem item : priceSummary.getPriceItems())
		//			{
		//				//one sub transaction per item
		//				createPayDef(defs, item, priceSummary);
		//			}
		//
		//			Plan plan;
		//			try
		//			{
		//				APIContext context = paypalService.getContext(getPayParam("PAYPAL.api.key"), keyProvider.getBase64String(storeAlias));
		//				plan = paypalService.createPaymentPlan(context, defs, appUrl + "/confirmPayment/paypal?op=confirm&mode=" + EPaymentMode.RECURRING + "&payment=" + payid, appUrl + "/confirmPayment/paypal?op=deny&mode=" + EPaymentMode.RECURRING + "&payment=" + payid, priceSummary.getName(), priceSummary.getName());
		//				plan = paypalService.activatePaymentPlan(context, plan);
		//				Agreement agreement = paypalService.createBillingAgreement(context, plan, priceSummary.getName(), priceSummary.getName());
		//				payments.setRedirectUrl(paypalService.fetchApprovalUrl(agreement.getLinks()).getHref());
		//				payments.setPayId(plan.getId());
		//				payments.setToken(agreement.getToken());
		//
		//			}
		//			catch (PayPalRESTException | MalformedURLException | UnsupportedEncodingException e)
		//			{
		//				payments.setState(EPaymentState.CREATED_ERROR);
		//				LOGGER.error("error in payments", e);
		//				throw new CBusinessException(EErrorResponse.PAYMENT_ERROR);
		//			}
		//		}
		getPersistence().update(payments);

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
		String operation = response.get("op");
		String mode = response.get("mode");

//		if (EPaymentMode.ONETIME.toString().equals(mode))
//		{
		PaymentRealization payments = getPersistence().getPaymentById(myPaymentId);
		
			//protection for DOS attacks
			if (payments == null)
			{
				throw new InvalidRequestException("payment not found");
			}

			String payerId = response.get("PayerID");
			String paymentId = response.get("paymentId");

			com.paypal.api.payments.Payment payment;
			try
			{
				APIContext context = getContext(ctx.getApiKey().get(), ctx.getApiSecret().get());

				payment = getPayment(context, paymentId);
			}
			catch (PayPalRESTException e)
			{
				throw new ConfigurationException(e);
			}
			if (!payment.getPayer().getPayerInfo().getPayerId().equals(payerId))
			{
				throw new DecryptionException("fraud attempt");
			}

			if ("deny".equalsIgnoreCase(operation))
			{
				getActions().handleCancel(payments);
			}
			else
			{
				try
				{
					APIContext context = getContext(ctx.getApiKey().get(), ctx.getApiSecret().get());
					
					com.paypal.api.payments.Payment executedPayment = executePayment(context, payerId, paymentId);
					acceptPayment(payments, executedPayment);
					getActions().handleSuccess(payments);


				}
				catch (PayPalRESTException e)
				{
					
					getActions().handleCancel(payments);

				}
			}
			payments.setBankResponse(JSONFormatter.toJSON(payment));
			getPersistence().update(payments);
			return payments;
//		}
//		else
//		{
//			//			recurrent 
//			// returning 
//			String token = response.get("token");
//			CPayments payments = paymentDao.findByToken(token, EPaymentType.PAYPAL);
//			if (payments == null)
//			{
//				throw new CBusinessException(EErrorResponse.NOT_FOUND);
//			}
//
//			try
//			{
//				APIContext context = paypalService.getContext(getPayParam("PAYPAL.api.key"), keyProvider.getBase64String(storeAlias));
//
//				paypalService.executeAgreement(context, token);
//				//TODO ako ziskat transaction id????
//				acceptPayment(payments, null);
//				return new CPaymentProcessResponse(payments.getId());
//
//			}
//			catch (PayPalRESTException e)
//			{
//				LOGGER.error("Recurring payment failed", e);
//				cancelPayment(payments);
//				return new CPaymentProcessResponse(payments.getId());
//
//			}
//		}

		
	}


	/**
	 * Creates the payment.
	 *
	 * @param transactions the transactions
	 * @param returnUrl the return url
	 * @param cancelUrl the cancel url
	 * @return the payment
	 * @throws PayPalRESTException the pay pal rest exception
	 */

	public com.paypal.api.payments.Payment createPayment (APIContext apiContext, List<Transaction> transactions, String returnUrl, String cancelUrl) throws PayPalRESTException
	{
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(returnUrl);
		payment.setRedirectUrls(redirectUrls);

		com.paypal.api.payments.Payment createdPayment = payment.create(apiContext);
		return createdPayment;

	}

	/**
	 * Validate payment.
	 *
	 * @param payerId the payer id
	 * @param paymentid the paymentid
	 * @return the payment
	 * @throws PayPalRESTException the pay pal rest exception
	 */

	public com.paypal.api.payments.Payment executePayment (APIContext apiContext, String payerId, String paymentid) throws PayPalRESTException
	{
		com.paypal.api.payments.Payment payment = com.paypal.api.payments.Payment.get(apiContext, paymentid);

		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		com.paypal.api.payments.Payment newPayment = payment.execute(apiContext, paymentExecution);
		return newPayment;
	}

	public APIContext getNewDefaultContext (String id, String secret) throws PayPalRESTException
	{
		String accessToken = acquireAccessToken(id, secret);
		APIContext apiContext = new APIContext(accessToken);
		return apiContext;
	}

	/**
	 * Fetch approval url.
	 *
	 * @param createdPayment the created payment
	 * @return the links
	 */
	public Links fetchApprovalUrl (List<Links> links)
	{
		for (Links link : links)
		{
			if (link.getRel().equalsIgnoreCase("approval_url"))
			{
				return link;

			}

		}
		return null;
	}

	private Map<String, String> getSdkConfig ()
	{
		Map<String, String> sdkConfig = new HashMap<>();
		sdkConfig.put("mode", ctx.getMode());
		sdkConfig.put("http.UseProxy", StringUtils.isBlank(ctx.getProxyHost() + ctx.getProxyPort()) ? "false" : "true");
		sdkConfig.put("http.ProxyHost", ctx.getProxyHost());
		sdkConfig.put("http.ProxyPort", ctx.getProxyPort());
		//sdkConfig.put("service.EndPoint", "https://api.sandbox.paypal.com");

		return sdkConfig;
	}

	private String acquireAccessToken (String clientId, String clientSecret) throws PayPalRESTException
	{
		String accessToken = new OAuthTokenCredential(clientId, clientSecret, getSdkConfig()).getAccessToken();
		return accessToken;
	}

	public Plan createPaymentPlan (APIContext apiContext, List<PaymentDefinition> payDef, String returnUrl, String cancelUrl, String desc, String name) throws PayPalRESTException
	{

		Plan plan = new Plan();
		plan.setName(name);
		plan.setDescription(desc);
		plan.setPaymentDefinitions(payDef);
		plan.setType("FIXED");
		plan.setState("ACTIVE");
		MerchantPreferences mp = new MerchantPreferences(cancelUrl, returnUrl);
		mp.setMaxFailAttempts("3");
		mp.setAutoBillAmount("YES");
		mp.setInitialFailAmountAction("CONTINUE");
		plan.setMerchantPreferences(mp);
		Plan create = plan.create(apiContext);
		return create;
	}

	public Plan activatePaymentPlan (APIContext apiContext, Plan plan) throws PayPalRESTException
	{

		List<Patch> list = new ArrayList<>();
		Patch patch = new Patch("replace", "/");
		Plan pl = new Plan();
		pl.setState("ACTIVE");
		patch.setValue(pl);
		list.add(patch);
		plan.update(apiContext, list);
		return Plan.get(apiContext, plan.getId());
	}

//	public Agreement createBillingAgreement (APIContext apiContext, Plan plan, String name, String desc) throws MalformedURLException, UnsupportedEncodingException, PayPalRESTException
//	{
//		Payer payer = new Payer();
//		payer.setPaymentMethod("paypal");
//		Plan pl = new Plan();
//		pl.setId(plan.getId());
//
//		Agreement agreement = new Agreement(name, desc, new DateTime(DateTimeZone.UTC).plusHours(HOURS_TO_DEFFER_PAYMENT).toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss")) + "Z", payer, pl);
//		return agreement.create(apiContext);
//
//		//		return Plan.get(apiContext, plan.getId());
//
//	}

	public com.paypal.api.payments.Payment getPayment (APIContext apiContext, String paymentId) throws PayPalRESTException
	{
		com.paypal.api.payments.Payment payment = com.paypal.api.payments.Payment.get(apiContext, paymentId);
		return payment;
	}

	public Agreement executeAgreement (APIContext apiContext, String token) throws PayPalRESTException
	{
		Agreement agreement = Agreement.execute(apiContext, token);
		return agreement;
	}

	private APIContext context = null;
	
	public APIContext getContext (String id, String secret) throws PayPalRESTException
	{
		APIContext apiContext = getNewDefaultContext(id, secret);
		apiContext.setConfigurationMap(getSdkConfig());
		context = apiContext;
		return context;
	}

	public Plan getPlan (APIContext context, String id) throws PayPalRESTException
	{

		//gets agreeement by ID
		Plan plan = Plan.get(context, id);
		return plan;
	}


//	private void createPayDef (List<PaymentDefinition> paydefs, CPriceItem item, CPriceSummary priceSummary)
//	{
//		if (item.getPrice().compareTo(BigDecimal.ZERO) != 0)
//		{
//			Currency curr = new Currency();
//			curr.setCurrency(item.getCurrency().toString());
//			curr.setValue(amountToString(item.getPrice())); // TODO: ma tu byt cena a nazov itemu?
//
//			PaymentDefinition planSpec = new PaymentDefinition(item.getName(), "REGULAR", "1", getPayParam("PAYPAL.period"), priceSummary.getRecurringCount().toString(), curr);
//			paydefs.add(planSpec);
//		}
//	}

	private void createTransaction (List<Transaction> transactions, Payment priceSummary)
	{
		if (priceSummary.getAmount().compareTo(BigDecimal.ZERO) != 0)
		{
			Transaction transaction = new Transaction();
			transaction.setItemList(new ItemList().setItems(new ArrayList<Item>()));

			transaction.setDescription(priceSummary.getDescription());

			Amount amount = new Amount();
			amount.setCurrency(priceSummary.getCurrency().toString());
			amount.setTotal(amountToString(priceSummary.getAmount()));

			//				Details details=new Details();
			//				details.setSubtotal(amountToString(item.getAmount()));
			//				amount.setDetails(details);

			transaction.setAmount(amount);

			Item paypalItem = new Item();
			paypalItem.setCurrency(priceSummary.getCurrency().toString());
			paypalItem.setName(priceSummary.getDescription());
			paypalItem.setPrice(amountToString(priceSummary.getAmount()));
			//			paypalItem.setQuantity(StringUtils.trimToEmpty(Integer.toString(variant.getQty())));
			paypalItem.setQuantity("1");
			transaction.getItemList().getItems().add(paypalItem);

			transactions.add(transaction);
		}
	}

	private String amountToString (BigDecimal amount)
	{

		return amount.setScale(2, RoundingMode.UP).toPlainString();
	}


	private void acceptPayment (PaymentRealization payments, com.paypal.api.payments.Payment executedPayment)
	{

		// fill transaction id
		if (executedPayment != null && executedPayment.getTransactions() != null && !executedPayment.getTransactions().isEmpty())
		{
			Transaction transaction = executedPayment.getTransactions().get(0);
			if (transaction.getRelatedResources() != null && !transaction.getRelatedResources().isEmpty())
			{
				RelatedResources r = transaction.getRelatedResources().get(0);
				if (r.getSale() != null)
				{
					payments.setGatePayId(r.getSale().getId());
				}
			}
		}

	}
	
//	private EPaymentState mapState (String state)
//	{
//		switch (state.toLowerCase())
//		{
//			case "active":
//				return EPaymentState.APPROVED;
//			case "pending":
//				return EPaymentState.CREATED;
//			case "expired":
//				return EPaymentState.CANCELED;
//			case "suspend":
//				return EPaymentState.CREATED;
//			case "reactivate":
//				return EPaymentState.APPROVED;
//			case "cancel":
//				return EPaymentState.CANCELED;
//
//		}
//		return null;
//	}
//
//	private EPaymentState mapState (Payment payment)
//	{
//		switch (payment.getState().toLowerCase())
//		{
//			case "created":
//				return EPaymentState.CREATED;
//			case "approved":
//				return EPaymentState.APPROVED;
//			case "failed":
//				return EPaymentState.CANCELED;
//			case "canceled":
//				return EPaymentState.CANCELED;
//			case "expired":
//				return EPaymentState.CANCELED;
//			case "pending":
//				return EPaymentState.CREATED;
//			case "in_progress":
//				return EPaymentState.CREATED;
//
//		}
//		return null;
//	}


}
