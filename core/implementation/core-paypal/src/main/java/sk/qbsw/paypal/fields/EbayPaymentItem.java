/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.fields;

import java.util.HashMap;
import java.util.Map;

/**
 * EbayItemPaymentDetailsItem request fields.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class EbayPaymentItem implements RequestFields
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/**
	 * Instantiates a new ebay payment item.
	 */
	public EbayPaymentItem ()
	{
		nvpRequest = new HashMap<String, String>();
	}

	/**
	 * Auction item number. Character length: 765 single-byte characters
	 *
	 * @param itemNumber the new item number
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setItemNumber (String itemNumber) throws IllegalArgumentException
	{

		if (itemNumber.length() > 765)
		{
			throw new IllegalArgumentException("Item number can be maximum " + "765 characters long.");
		}
		nvpRequest.put("L_EBAYITEMNUMBER", itemNumber);
	}

	/**
	 * Auction transaction identification number. Character length: 255
	 * single-byte characters
	 *
	 * @param transactionNumber the new transaction number
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setTransactionNumber (String transactionNumber) throws IllegalArgumentException
	{

		if (transactionNumber.length() > 255)
		{
			throw new IllegalArgumentException("Transaction number can be " + "maximum 255 characters long.");
		}
		nvpRequest.put("L_EBAYITEMAUCTIONTXNID", transactionNumber);
	}

	/**
	 * Auction order identification number. Character length: 64 single-byte
	 * characters
	 *
	 * @param orderId the new order id
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setOrderId (String orderId) throws IllegalArgumentException
	{

		if (orderId.length() > 64)
		{
			throw new IllegalArgumentException("Order id can be maximum 64 " + "characters long.");
		}
		nvpRequest.put("L_EBAYITEMORDERID", orderId);
	}

	/**
	 * The unique identifier provided by eBay for this order from the buyer.
	 * Character length: 255 single-byte characters
	 *
	 * @param itemCartId the new item cart id
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setItemCartId (String itemCartId) throws IllegalArgumentException
	{

		if (itemCartId.length() > 255)
		{
			throw new IllegalArgumentException("Item cart id can be maximum " + "255 characters long.");
		}
		nvpRequest.put("L_EBAYITEMCARTID", itemCartId);
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

		return "instance of EbayPaymentDetailsItem class with the values: " + "nvpRequest: " + nvpRequest.toString();
	}
}
