/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.util.FormatFields;

/**
 * Recurring Payments Profile Details Fields.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class RecurringPaymentsProfileDetails implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new recurring payments profile details.
	 *
	 * @param profileStartDate  The date when billing for this profile begins.
	 * Must be a valid date, in UTC/GMT format.
	 * Note:
	 * The profile may take up to 24 hours for
	 * activation.
	 */
	public RecurringPaymentsProfileDetails (Date profileStartDate)
	{

		/*  paypal needs  Coordinated Universal Time (UTC/GMT), using ISO 8601
		    format, and of type ns:dateTime for Date/Time formats */
		String date = FormatFields.getDateTimeField(profileStartDate);

		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("PROFILESTARTDATE", date);
	}

	/**
	 * Sets the subscriber name.
	 *
	 * @param name  Full name of the person receiving the product or service
	 * paid for by the recurring payment. If not present, the name
	 * in the buyer's PayPal account is used.
	 * Character length and limitations: 32 single-byte characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setSubscriberName (String name) throws IllegalArgumentException
	{

		if (name.length() > 32)
		{
			throw new IllegalArgumentException("Name can be maximum 32 " + "characters");
		}
		nvpRequest.put("SUBSCRIBERNAME", name);
	}

	/**
	 * Sets the profile reference.
	 *
	 * @param referenceNumber   The merchant???s own unique reference or invoice
	 * number. Character length and limitations: 127
	 * single-byte alphanumeric characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setProfileReference (String referenceNumber) throws IllegalArgumentException
	{

		if (referenceNumber.length() > 32)
		{
			throw new IllegalArgumentException("Reference number can be " + "maximum 32 characters");
		}
		nvpRequest.put("SUBSCRIBERNAME", referenceNumber);
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

		return "instance of RecurringPaymentsProfileDetails class with the " + "values: nvpRequest: " + nvpRequest.toString();
	}
}
