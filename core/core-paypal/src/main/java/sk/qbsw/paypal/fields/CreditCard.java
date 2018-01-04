/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.util.FormatFields;
import sk.qbsw.paypal.util.Validator;

/**
 * Credit Card Details Fields.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class CreditCard implements RequestFields
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Type of credit card
	 *
	 * For UK, only Maestro, Solo, MasterCard, Discover, and Visa are
	 * allowable. For Canada, only MasterCard and Visa are allowable; Interac
	 * debit cards are not supported.
	 * Note:
	 * If the credit card type is Maestro or Solo, the CURRENCYCODE must be
	 * GBP. In addition, either STARTDATE or ISSUENUMBER must be specified.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum CreditCardType
	{

		/** The VISA. */
		VISA ("Visa"),

        /** The MASTER card. */
        MASTER_CARD ("MasterCard"),

        /** The DISCOVER. */
        DISCOVER ("Discover"),

        /** The AMEX. */
        AMEX ("Amex"),

        /** The MAESTRO. */
        MAESTRO ("Maestro"),

        /** The SOLO. */
        SOLO ("Solo");

		/** The value. */
		private String value;

		/**
		 * Instantiates a new credit card type.
		 *
		 * @param value the value
		 */
		private CreditCardType (String value)
		{
			this.value = value;
		}

		/**
		 * returns value name for nvp request.
		 *
		 * @return the value
		 */
		String getValue ()
		{
			return value;
		}
	}

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new credit card.
	 *
	 * @param cardType      Type of credit card. For UK, only Maestro, Solo,
	 * MasterCard, Discover, and Visa are allowable.
	 * For Canada, only MasterCard and Visa are allowable;
	 * Interac debit cards are not supported.
	 * Note:
	 * If the credit card type is Maestro or Solo, the
	 * CURRENCYCODE must be GBP. In addition, either
	 * STARTDATE or ISSUENUMBER must be specified.
	 * @param cardNumber    Character length and limitations: numeric
	 * characters only. No spaces or punctuation. Must
	 * conform with modulo and length required by each
	 * credit card type.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public CreditCard (CreditCardType cardType, String cardNumber)
	{

		if (!Validator.isValidLuhn(cardNumber))
		{
			throw new IllegalArgumentException("Card number - " + cardNumber + " is not valid.");
		}

		nvpRequest = new HashMap<>();

		nvpRequest.put("CREDITCARDTYPE", cardType.getValue());
		nvpRequest.put("ACCT", cardNumber);
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate    Credit card expiration date. This field is required
	 * if you are using recurring payments with direct
	 * payments. Only month and a year is used from
	 * supplied Date argument.
	 */
	public void setExpiryDate (Date expiryDate)
	{
		nvpRequest.put("EXPDATE", FormatFields.getCardDateField(expiryDate));
	}

	/**
	 * Sets the cV v2.
	 *
	 * @param cvv2  Card Verification Value, version 2. Your Merchant Account
	 * settings determine whether this field is required.
	 * Character length for Visa, MasterCard, and Discover:
	 * exactly three digits.Character length for American Express:
	 * exactly four digits.To comply with credit card processing
	 * regulations, you must not store this value after a
	 * transaction has been completed.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setCVV2 (int cvv2)
	{

		String cardType = nvpRequest.get("CREDITCARDTYPE");
		int numberLength = String.valueOf(cvv2).length();

		// amex cvv2 is 4 digits long, for others 3 digits
		if ((CreditCardType.AMEX.getValue().equals(cardType) && numberLength != 4) || (numberLength != 3))
		{
			throw new IllegalArgumentException("Please provide correct cvv2");
		}

		nvpRequest.put("CVV2", Integer.toString(cvv2));
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate Month and year that Maestro or Solo card was issued.
	 * Only month and a year is used from supplied Date
	 * argument.
	 */
	public void setStartDate (Date startDate)
	{
		nvpRequest.put("STARTDATE", FormatFields.getCardDateField(startDate));
	}

	/**
	 * Sets the issue number.
	 *
	 * @param issueNumber   Issue number of Maestro or Solo card. Character
	 * length: two numeric digits maximum.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setIssueNumber (int issueNumber)
	{

		int numberLength = String.valueOf(issueNumber).length();

		if (numberLength > 2)
		{
			throw new IllegalArgumentException("Issue number cannot have more " + "than 2 numeric digits");
		}

		nvpRequest.put("ISSUENUMBER", String.valueOf(issueNumber));
	}

	/**
	 * @see sk.qbsw.paypal.fields.RequestFields#getNVPRequest()
	 */
	public Map<String, String> getNVPRequest ()
	{
		return new HashMap<>(nvpRequest);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{

		return "instance of CreditCard class with the values: " + "nvpRequest: " + nvpRequest.toString();
	}
}
