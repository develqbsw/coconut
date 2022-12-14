/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.util.Validator;

/**
 * Payer Information Fields.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class PayerInformation implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Status of payer.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum PayerStatus
	{
		
		/** The verified. */
		verified, 
 /** The unverified. */
 unverified
	}

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new payer information.
	 */
	public PayerInformation ()
	{
		this.nvpRequest = new HashMap<String, String>();
	}

	/** 
	 * @see sk.qbsw.paypal.fields.RequestFields#getNVPRequest()
	 */
	public Map<String, String> getNVPRequest ()
	{
		return new HashMap<String, String>(nvpRequest);
	}

	/**
	 * Email address of payer. Email address has to be valid and cannot exceed
	 * 127 characters, otherwise exception is thrown.
	 *
	 * @param email 	Character length and limitations: 127 single-byte
	 * characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setEmail (String email) throws IllegalArgumentException
	{

		if (!Validator.isValidEmail(email))
		{
			throw new IllegalArgumentException("Email is not valid");
		}

		if (email.length() > 127)
		{
			throw new IllegalArgumentException("Email can be maximum 127 " + "characters long.");
		}
		nvpRequest.put("EMAIL", email);
	}

	/**
	 * Unique PayPal customer account identification number.
	 * Character length and limitations:13 single-byte alphanumeric characters.
	 *
	 * @param payerId the new payer id
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setPayerId (String payerId) throws IllegalArgumentException
	{

		if (payerId == null || payerId.length() > 127)
		{
			throw new IllegalArgumentException("PayerId can be maximum 127 " + "characters long.");
		}
		nvpRequest.put("PAYERID", payerId);
	}

	/**
	 * Sets status of payer.
	 *
	 * @param status the new payer status
	 */
	public void setPayerStatus (PayerStatus status)
	{
		nvpRequest.put("PAYERSTATUS", status.toString());
	}

	/**
	 * Payer's country of residence.
	 *
	 * @param country the new country
	 */
	public void setCountry (Country country)
	{
		nvpRequest.put("COUNTRYCODE", country.toString());
	}

	/**
	 * Payer's business name. Throws exception if name is null or exceeds 127
	 * characters.
	 *
	 * @param name 	Character length and limitations: 127 single-byte characters
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setBusinessName (String name) throws IllegalArgumentException
	{
		if (name == null || name.length() > 127)
		{
			throw new IllegalArgumentException("Name can be maximum 127 " + "characters long.");
		}
		nvpRequest.put("BUSINESS", name);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{

		return "instance of PayerInformation class with the values: " + "nvpRequest: " + nvpRequest.toString();
	}
}
