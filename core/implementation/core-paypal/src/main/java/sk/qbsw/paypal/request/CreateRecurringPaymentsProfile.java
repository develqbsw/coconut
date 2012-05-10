/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;
import sk.qbsw.paypal.fields.ActivationDetails;
import sk.qbsw.paypal.fields.Address;
import sk.qbsw.paypal.fields.BillingPeriodDetails;
import sk.qbsw.paypal.fields.CreditCard;
import sk.qbsw.paypal.fields.PayerInformation;
import sk.qbsw.paypal.fields.PayerName;
import sk.qbsw.paypal.fields.RecurringPaymentsProfileDetails;
import sk.qbsw.paypal.fields.ScheduleDetails;
import sk.qbsw.paypal.fields.ShipToAddress;

/**
 * Create a recurring payments profile.
 *
 * You must invoke the CreateRecurringPaymentsProfile API operation for each
 * profile you want to create. The API operation creates a profile and an
 * associated billing agreement.
 *
 * Note:
 * There is a one-to-one correspondence between billing agreements and
 * recurring payments profiles. To associate a a recurring payments profile
 * with its billing agreement, the description in the recurring payments
 * profile must match the description of a billing agreement. Use
 * SetExpressCheckout to initiate creation of a billing agreement.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>.
 * @author Dalibor Rak
 */
public final class CreateRecurringPaymentsProfile implements Request
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Method value of this request. */
	private static final String METHOD_NAME = "CreateRecurringPaymentsProfile";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;

	/**
	 * Instantiates a new creates the recurring payments profile.
	 */
	private CreateRecurringPaymentsProfile ()
	{

		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
	}

	/**
	 * Token is appended to the return or cancel url set in SetExpressCheckout.
	 *
	 * @param token A timestamped token, the value of which was returned in the
	 * response to the first call to SetExpressCheckout. You can
	 * also use the token returned in the
	 * SetCustomerBillingAgreement response. Either this token or
	 * a credit card number is required. If you include both token
	 * and credit card number, the token is used and credit card
	 * number is ignored. Call CreateRecurringPaymentsProfile once
	 * for each billing agreement included in SetExpressCheckout
	 * request and use the same token for each call. Each
	 * CreateRecurringPaymentsProfile request creates a single
	 * recurring payments profile.
	 * Note:
	 * Tokens expire after approximately 3 hours.
	 * @param details the details
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public CreateRecurringPaymentsProfile (String token, ScheduleDetails details) throws IllegalArgumentException
	{

		this();

		if (token.length() != 20)
		{
			throw new IllegalArgumentException("Invalid token argument");
		}

		nvpRequest.put("TOKEN", token);
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	/**
	 * Instantiates a new creates the recurring payments profile.
	 *
	 * @param card the card
	 */
	public CreateRecurringPaymentsProfile (CreditCard card)
	{

		this();

		nvpRequest.putAll(new HashMap<String, String>(card.getNVPRequest()));
	}

	/**
	 * Sets the recurring payments profile details.
	 *
	 * @param details the new recurring payments profile details
	 */
	public void setRecurringPaymentsProfileDetails (RecurringPaymentsProfileDetails details)
	{

		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	/**
	 * Sets the billing period details.
	 *
	 * @param details the new billing period details
	 */
	public void setBillingPeriodDetails (BillingPeriodDetails details)
	{
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	/**
	 * Sets the activation details.
	 *
	 * @param details the new activation details
	 */
	public void setActivationDetails (ActivationDetails details)
	{
		nvpRequest.putAll(new HashMap<String, String>(details.getNVPRequest()));
	}

	/**
	 * Sets the ship to address.
	 *
	 * @param address the new ship to address
	 */
	public void setShipToAddress (ShipToAddress address)
	{
		nvpRequest.putAll(new HashMap<String, String>(address.getNVPRequest()));
	}

	/**
	 * Sets the payer information.
	 *
	 * @param payer the new payer information
	 */
	public void setPayerInformation (PayerInformation payer)
	{
		nvpRequest.putAll(new HashMap<String, String>(payer.getNVPRequest()));
	}

	/**
	 * Sets the payer name.
	 *
	 * @param name the new payer name
	 */
	public void setPayerName (PayerName name)
	{
		nvpRequest.putAll(new HashMap<String, String>(name.getNVPRequest()));
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress (Address address)
	{
		nvpRequest.putAll(new HashMap<String, String>(address.getNVPRequest()));
	}

	/** 
	 * @see sk.qbsw.paypal.request.Request#getNVPRequest()
	 */
	public Map<String, String> getNVPRequest ()
	{
		return new HashMap<String, String>(nvpRequest);
	}

	/** 
	 * @see sk.qbsw.paypal.request.Request#setNVPResponse(sk.qbsw.paypal.core.NVPResponse)
	 */
	public void setNVPResponse (NVPResponse nvpResponse)
	{
		this.nvpResponse = new NVPResponse(nvpResponse);
	}

	/** 
	 * @see sk.qbsw.paypal.request.Request#getNVPResponse()
	 */
	public NVPResponse getNVPResponse ()
	{
		return new NVPResponse(nvpResponse);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{

		StringBuffer str = new StringBuffer("instance of CreateRecurringPaymentsProfile");
		str.append("class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}
}
