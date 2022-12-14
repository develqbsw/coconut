/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.util.Validator;

/**
 * The Class MassPayItem.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class MassPayItem implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;


	/**
	 * Instantiates a new mass pay item.
	 *
	 * @param key       key in nvp request string
	 * @param value     value in nvp request string
	 * @param amount    payment amount
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	private MassPayItem (String key, String value, String amount) throws IllegalArgumentException
	{

		if (!Validator.isValidAmount(amount))
		{
			throw new IllegalArgumentException("amount - " + amount + " is not valid");
		}

		nvpRequest = new HashMap<String, String>();
		nvpRequest.put(key, value);
		nvpRequest.put("L_AMT", amount);
	}

	/**
	 * Gets the new email mass pay item.
	 *
	 * @param email     Email address of recipient. Character length and
	 * limitations: 127 single-byte characters maximum.
	 * @param amount    Payment amount.
	 * @return the new email mass pay item
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static MassPayItem getNewEmailMassPayItem (String email, String amount) throws IllegalArgumentException
	{

		if (!Validator.isValidEmail(email))
		{
			throw new IllegalArgumentException("email is not valid");
		}
		if (email.length() > 127)
		{
			throw new IllegalArgumentException("email cannot be longer than " + "127 characters");
		}
		return new MassPayItem("L_EMAIL", email, amount);
	}

	/**
	 * Gets the new receiver id mass pay item.
	 *
	 * @param receiverId    Unique PayPal customer account number. This value
	 * corresponds to the value of PayerID returned by
	 * GetTransactionDetails.
	 * Character length and limitations: 17 single-byte
	 * characters maximum.
	 * @param amount        Payment amount.
	 * @return the new receiver id mass pay item
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static MassPayItem getNewReceiverIdMassPayItem (String receiverId, String amount) throws IllegalArgumentException
	{

		if (receiverId.length() > 17)
		{
			throw new IllegalArgumentException("receiverId cannot be longer " + "than 17 characters");
		}
		return new MassPayItem("L_RECEIVERID", receiverId, amount);
	}

	/**
	 * Sets the unique id.
	 *
	 * @param id    Transaction-specific identification number for tracking in
	 * an accounting system. Character length and limitations:
	 * 30 single-byte characters. No whitespace allowed.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setUniqueId (String id) throws IllegalArgumentException
	{

		if (id.contains(" "))
		{
			throw new IllegalArgumentException("id cannot contain white space");
		}
		if (id.length() > 30)
		{
			throw new IllegalArgumentException("id cannot be longer than 30" + " characters");
		}
		nvpRequest.put("L_UNIQUEID", id);
	}

	/**
	 * Sets the note.
	 *
	 * @param note  Custom note for each recipient. Character length and
	 * limitations: 4,000 single-byte alphanumeric characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setNote (String note) throws IllegalArgumentException
	{

		if (note.length() > 4000)
		{
			throw new IllegalArgumentException("note cannot be longer than 4000" + " characters");
		}
		nvpRequest.put("L_NOTE", note);
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

		return "instance of MassPayItem class with the values: " + "nvpRequest: " + nvpRequest.toString();
	}
}
