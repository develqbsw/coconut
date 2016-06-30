/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.util.Validator;

/**
 * The Class ShippingOptions.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class ShippingOptions implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new shipping options.
	 */
	public ShippingOptions ()
	{
		nvpRequest = new HashMap<String, String>();
	}

	/**
	 * Required if specifying the Callback URL. When the value
	 * of this flat rate shipping option is true, PayPal selects it by default
	 * for the buyer and reflects it in the "default" total. Note: There must
	 * be ONE and ONLY ONE default. It is not OK to have no default.
	 *
	 * @param isDefault the new default shipping option
	 */
	public void setDefaultShippingOption (boolean isDefault)
	{

		String option = (isDefault) ? "true" : "false";
		nvpRequest.put("L_SHIPPINGOPTIONISDEFAULT", option);
	}

	/**
	 * Required if specifying the Callback URL. The internal
	 * name of the shipping option such as Air, Ground, Expedited, and so
	 * forth. Character length and limitations: 50 character-string.
	 *
	 * @param name the new shipping name
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setShippingName (String name) throws IllegalArgumentException
	{

		if (name.length() > 50)
		{
			throw new IllegalArgumentException("Name cannot exceed 50 " + "characters");
		}
		nvpRequest.put("L_SHIPPINGOPTIONNAME", name);
	}

	/**
	 * Required if specifying the Callback URL. The label for
	 * the shipping option as displayed to the user. Examples include: Air:
	 * Next Day, Expedited: 3-5 days, Ground: 5-7 days, and so forth. Shipping
	 * option labels can be localized based on the buyer’s locale, which PayPal
	 * sends to your website as a parameter value in the callback request.
	 * Character length and limitations: 50 character-string.
	 *
	 * @param label the new shipping label
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setShippingLabel (String label) throws IllegalArgumentException
	{

		if (label.length() > 50)
		{
			throw new IllegalArgumentException("Label cannot exceed 50 " + "characters");
		}
		nvpRequest.put("L_SHIPPINGOPTIONLABEL", label);
	}

	/**
	 * Required if specifying the Callback URL. The amount of
	 * the flat rate shipping option. Limitations: Must not exceed $10,000 USD
	 * in any currency. No currency symbol. Must have two decimal places,
	 * decimal separator must be a period (.).
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
		nvpRequest.put("L_SHIPPINGOPTIONAMOUNT", amount);
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
