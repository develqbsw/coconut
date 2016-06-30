/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;
import sk.qbsw.paypal.fields.Address;
import sk.qbsw.paypal.fields.Payment;
import sk.qbsw.paypal.fields.PaymentAction;
import sk.qbsw.paypal.fields.UserSelectedOptions;

/**
 * The Class DoExpressCheckoutPayment.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>.
 * @author Dalibor Rak
 */
public final class DoExpressCheckoutPayment implements Request
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Method value of this request. */
	private static final String METHOD_NAME = "DoExpressCheckoutPayment";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;

	/**
	 * Instantiates a new do express checkout payment.
	 *
	 * @param payment 	Should be the same as for SetExpressCheckout
	 * @param token         PayPal token
	 * @param paymentAction How you want to obtain payment
	 * @param payerId       Unique PayPal customer account identification
	 * number as returned by GetExpressCheckoutDetails
	 * response
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public DoExpressCheckoutPayment (Payment payment, String token, PaymentAction paymentAction, String payerId) throws IllegalArgumentException
	{

		if (token.length() != 20)
		{
			throw new IllegalArgumentException("Invalid token argument");
		}
		if (payerId.length() != 13)
		{
			throw new IllegalArgumentException("Invalid payer id");
		}

		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
		/* insert payment values */
		HashMap<String, String> nvp = new HashMap<String, String>(payment.getNVPRequest());
		nvpRequest.putAll(nvp);
		nvpRequest.put("TOKEN", token);
		nvpRequest.put("PAYMENTACTION", paymentAction.getValue());
		nvpRequest.put("PAYERID", payerId);
	}

	/**
	 * Flag to indicate whether you want the results returned by Fraud 
	 * Management Filters. By default this is false. 
	 * 
	 * @param fmf	true: receive FMF details
	 *				false: do not receive FMF details (default)
	 */
	public void setReturnFMF (boolean fmf)
	{

		int x = (fmf) ? 1 : 0;
		nvpRequest.put("RETURNFMFDETAILS", Integer.toString(x));
	}

	/**
	 * Sets user selected options.
	 *
	 * @param userOptions the new user selected options
	 */
	public void setUserSelectedOptions (UserSelectedOptions userOptions)
	{

		HashMap<String, String> nvp = new HashMap<String, String>(userOptions.getNVPRequest());
		nvpRequest.putAll(nvp);
	}

	/**
	 * Sets address fields.
	 *
	 * @param address the new address
	 */
	public void setAddress (Address address)
	{

		HashMap<String, String> nvp = new HashMap<String, String>(address.getNVPRequest());
		nvpRequest.putAll(nvp);
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

		StringBuffer str = new StringBuffer("instance of SetExpressCheckout ");
		str.append("class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}
}
