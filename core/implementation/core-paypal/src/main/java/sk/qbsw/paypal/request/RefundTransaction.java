/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;
import sk.qbsw.paypal.util.Validator;

/**
 * RefundTransaction Request Message.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>.
 * @author Dalibor Rak
 */
public final class RefundTransaction implements Request
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Type of refund you are making.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	private enum RefundType
	{
		
		/** The OTHER. */
		OTHER, 
 /** The FULL. */
 FULL, 
 /** The PARTIAL. */
 PARTIAL
	}

	/** Method value of this request. */
	private static final String METHOD_NAME = "RefundTransaction";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;

	/**
	 * Transaction id cannot be longer than 17 characters and cannot be null,
	 * if it is, exception is thrown.
	 *
	 * @param transactionId Unique identifier of a transaction.
	 * Character length and limitations: 17 single-byte
	 * alphanumeric characters.
	 * @param refund 	type of refund you are making
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	private RefundTransaction (String transactionId, RefundType refund) throws IllegalArgumentException
	{

		/* validation */
		if (transactionId == null || transactionId.length() > 17)
		{
			throw new IllegalArgumentException("Transaction id cannot be " + "longer than 17 characters");
		}

		/* set instance variables */
		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("TRANSACTIONID", transactionId);
		nvpRequest.put("REFUNDTYPE", refund.toString());
	}

	/**
	 * Transaction id cannot be longer than 17 characters and cannot be null,
	 * if it is, exception is thrown.
	 *
	 * @param transactionId Unique identifier of a transaction.
	 * Character length and limitations: 17 single-byte
	 * alphanumeric characters.
	 * @return 			RefundTransaction where type is set to Full.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static RefundTransaction getFullRefund (String transactionId) throws IllegalArgumentException
	{
		return new RefundTransaction(transactionId, RefundType.FULL);
	}

	/**
	 * Transaction id and amount has to be valid, otherwise exception is thrown.
	 *
	 * @param transactionId Unique identifier of a transaction.
	 * Character length and limitations: 17 single-byte
	 * alphanumeric characters.
	 * @param amount 	Amount to reauthorize.
	 * Limitations: Value is a positive number which cannot
	 * exceed $10,000 USD in any currency. No currency
	 * symbol. Must have two decimal places, decimal
	 * separator must be a period (.).
	 * @return 			RefundTransaction where type is set tu Partial.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static RefundTransaction getPartialRefund (String transactionId, String amount) throws IllegalArgumentException
	{

		RefundTransaction rt = new RefundTransaction(transactionId, RefundType.PARTIAL);
		rt.setAmount(amount);
		return rt;
	}

	/**
	 * Transaction id cannot be longer than 17 characters and cannot be null,
	 * if it is, exception is thrown.
	 *
	 * @param transactionId Unique identifier of a transaction.
	 * Character length and limitations: 17 single-byte
	 * alphanumeric characters.
	 * @return 			RefundTransaction where type is set to Other.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static RefundTransaction getOtherRefund (String transactionId) throws IllegalArgumentException
	{
		return new RefundTransaction(transactionId, RefundType.OTHER);
	}

	/**
	 * Transaction id and amount has to be valid, otherwise exception is thrown.
	 *
	 * @param transactionId Unique identifier of a transaction.
	 * Character length and limitations: 17 single-byte
	 * alphanumeric characters.
	 * @param amount 	Amount to reauthorize.
	 * Limitations: Value is a positive number which cannot
	 * exceed $10,000 USD in any currency. No currency
	 * symbol. Must have two decimal places, decimal
	 * separator must be a period (.).
	 * @return 			RefundTransaction where type is set tu Other.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public static RefundTransaction getOtherRefund (String transactionId, String amount) throws IllegalArgumentException
	{

		RefundTransaction rt = new RefundTransaction(transactionId, RefundType.OTHER);
		rt.setAmount(amount);
		return rt;
	}

	/**
	 * Refund amount. Amount is required if RefundType is Partial.
	 * NOTE:If RefundType is Full, do not set the Amount.
	 *
	 * @param amount Amount to reauthorize.
	 * Limitations: Value is a positive number which cannot
	 * exceed $10,000 USD in any currency. No currency symbol.
	 * Must have two decimal places, decimal separator must be
	 * a period (.).
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	private void setAmount (String amount) throws IllegalArgumentException
	{

		if (!Validator.isValidAmount(amount))
		{
			throw new IllegalArgumentException("Amount is not valid. Amount " + "has to have exactly two decimal " + "places seaprated by \".\" - example: \"50.00\"");
		}
		nvpRequest.put("AMT", amount);
	}

	/**
	 * Custom memo about the refund.
	 *
	 * @param note Character length and limitations: 255 single-byte
	 * alphanumeric characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setNote (String note) throws IllegalArgumentException
	{

		if (note == null || note.length() > 255)
		{
			throw new IllegalArgumentException("Note cannot be longer than 255 " + "characters");
		}
		nvpRequest.put("NOTE", note);
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

		StringBuffer str = new StringBuffer("instance of ");
		str.append("GetBillingAgreementCustomer class with the values: ");
		str.append("nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}
}
