/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.HashMap;
import java.util.Map;

/**
 * Billing Agreement Details Fields.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class BillingAgreement implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * PayPal payment you require for the billing agreement.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum PaymentType
	{

		/** The ANY. */
		ANY ("Any"), /** The INSTAN t_ only. */
 INSTANT_ONLY ("InstantOnly");

		/** The name. */
		private String name;

		/**
		 * Instantiates a new payment type.
		 *
		 * @param name the name
		 */
		private PaymentType (String name)
		{
			this.name = name;
		}

		/**
		 * returns key name for nvp request.
		 *
		 * @return the name
		 */
		String getName ()
		{
			return name;
		}
	}

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new billing agreement.
	 */
	public BillingAgreement ()
	{
		nvpRequest = new HashMap<String, String>();
	}

	/**
	 * Type of billing agreement.
	 * For recurring payments, this field must be set to
	 * <b>RecurringPayments</b> and description (<b>setDescription</b>) MUST be
	 * set as well.
	 * In this case, you can specify up to ten
	 * billing agreements. Note: Other defined values are not valid.
	 *
	 * @param billingType the new billing type
	 */
	public void setBillingType (String billingType)
	{
		nvpRequest.put("L_BILLINGTYPE", billingType);
	}

	/**
	 * Description of goods or services associated with the billing agreement,
	 * which is required for each recurring payment billing agreement. PayPal
	 * recommends that the description contain a brief summary of the billing
	 * agreement terms and conditions. For example, customer will be billed at
	 * "9.99 per month for 2 years". Character length and limitations:
	 * 127 single-byte alphanumeric bytes.
	 *
	 * @param description the new description
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setDescription (String description) throws IllegalArgumentException
	{

		if (description.length() > 127)
		{
			throw new IllegalArgumentException("Description cannot exceed 127 " + "characters");
		}
		nvpRequest.put("L_BILLINGAGREEMENTDESCRIPTION", description);
	}

	/**
	 * Specifies type of PayPal payment you require for the billing agreement.
	 * Note: For recurring payments, this field is ignored.
	 *
	 * @param paymentType the new payment type
	 */
	public void setPaymentType (PaymentType paymentType)
	{
		nvpRequest.put("L_PAYMENTTYPE", paymentType.getName());
	}

	/**
	 * Custom annotation field for your own use. Note: For recurring payments,
	 * this field is ignored. Character length and limitations: 256 single-byte
	 * alphanumeric bytes.
	 *
	 * @param field the new custom field
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setCustomField (String field) throws IllegalArgumentException
	{

		if (field.length() > 256)
		{
			throw new IllegalArgumentException("Field cannot exceed 256 " + "characters");
		}
		nvpRequest.put("L_BILLINGAGREEMENTCUSTOM", field);
	}

	/** 
	 * @see sk.qbsw.paypal.fields.RequestFields#getNVPRequest()
	 */
	public Map<String, String> getNVPRequest ()
	{
		return new HashMap<String, String>(nvpRequest);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{

		return "instance of ShippingOptions class with the values: " + "nvpRequest: " + nvpRequest.toString();
	}
}
