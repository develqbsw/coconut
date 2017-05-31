/**
 * 
 */
package sk.qbsw.core.pay.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import sk.qbsw.core.pay.base.response.AbstractBankResponse;
import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public abstract class PaymentProcessor
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessor.class);
	private PaymentPersistence persistence;
	private PaymentActions actions;

	/**
	 * vyskladá objekt ktorý obsahuje vsetky údaje potrebne pre platbonu branu. 
	 * @param payment
	 * @return
	 * @author martinkovic
	 * @version 1.15.0
	 * @since 1.15.0
	 */
	public abstract PaymentRealization createPayment(Payment payment);
	
	
	/**
	 * spracuje volanie z banky. oznaci platbu ako uspesnu/neuspesnu. 
	 * @param response
	 * @author martinkovic
	 * @version 1.15.0
	 * @return 
	 * @since 1.15.0
	 */
	public abstract PaymentRealization handleBankPaymentResponse(AbstractBankResponse response);
	
	
	protected String makePaymentURL (BankResponse pay, String url)
	{
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		for (Entry<String, String> param : pay.getParams().entrySet())
		{
			try
			{
				builder.queryParam(param.getKey(), URLEncoder.encode(param.getValue(), "UTF-8"));
			}
			catch (UnsupportedEncodingException e)
			{
				LOGGER.error("error in parsing param [" + param.getKey() + "," + param.getValue() + "]", e);
			}

		}

		return builder.build().toUriString();
	}


	public PaymentActions getActions ()
	{
		return actions;
	}


	public void setActions (PaymentActions actions)
	{
		this.actions = actions;
	}


	public PaymentPersistence getPersistence ()
	{
		return persistence;
	}


	public void setPersistence (PaymentPersistence persistence)
	{
		this.persistence = persistence;
	}
	
}
