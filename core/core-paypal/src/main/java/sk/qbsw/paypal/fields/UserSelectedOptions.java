/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.util.Validator;

/**
 * User Selected Options Type Fields.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class UserSelectedOptions implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * How the options that were presented to the user were determined.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum ShippingCalculation
	{
		
		/** The CALLBACK. */
		CALLBACK, 
 /** The FLATRATE. */
 FLATRATE
	}

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new user selected options.
	 */
	public UserSelectedOptions ()
	{
		nvpRequest = new HashMap<String, String>();
	}

	/**
	 * Describes how the options that were presented to the user were
	 * determined.
	 *
	 * @param calculation the new shipping calculation
	 */
	public void setShippingCalculation (ShippingCalculation calculation)
	{
		nvpRequest.put("SHIPPINGCALCULATIONMODE", calculation.toString());
	}

	/**
	 * The Yes/No option that you chose for insurance.
	 *
	 * @param insurance the new insurance
	 */
	public void setInsurance (boolean insurance)
	{

		String x = (insurance) ? "Yes" : "No";
		nvpRequest.put("INSURANCEOPTIONSELECTED", x);
	}

	/**
	 * Is true if the buyer chose the default shipping option.
	 *
	 * @param option the new default shipping option
	 */
	public void setDefaultShippingOption (boolean option)
	{

		String x = (option) ? "true" : "false";
		nvpRequest.put("SHIPPINGOPTIONISDEFAULT", x);

		/* Is true if the buyer chose the default shipping option. */
		if (option)
		{
			nvpRequest.put("SHIPPINGOPTIONNAME", x);
		}
	}

	/**
	 * The shipping amount that was chosen by the buyer Limitations: Must not
	 * exceed $10,000 USD in any currency. No currency symbol. Must have two
	 * decimal places, decimal separator must be a period (.).
	 *
	 * @param amount the new shipping amount
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setShippingAmount (String amount) throws IllegalArgumentException
	{

		if (!Validator.isValidAmount(amount))
		{
			throw new IllegalArgumentException("Amount " + amount + " is not valid. Amount has to have exactly two decimal " + "places seaprated by \".\" - example: \"50.00\"");
		}

		nvpRequest.put("SHIPPINGOPTIONAMOUNT", amount);
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

		return "instance of UserSelectedOptions class with the values: " + "nvpRequest: " + nvpRequest.toString();
	}
}
