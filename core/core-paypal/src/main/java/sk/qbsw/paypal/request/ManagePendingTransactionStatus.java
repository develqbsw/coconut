/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;

/**
 * The Class ManagePendingTransactionStatus.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class ManagePendingTransactionStatus implements Request
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The operation you want to perform on the transaction.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum Action
	{
		
		/** accepts the payment. */
		ACCEPT,

		/** rejects the payment. */
		DENY;
	}

	/** Method value of this request. */
	private static final String METHOD_NAME = "ManagePendingTransactionStatus";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;

	/**
	 * Returns instance of ManagePendingTransaction class. If arguments are not
	 * valid, exception is thrown.
	 *
	 * @param transactionId The transaction ID of the payment transaction.
	 * @param action 	The operation you want to perform on the transaction
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public ManagePendingTransactionStatus (String transactionId, Action action) throws IllegalArgumentException
	{

		if (transactionId == null || transactionId.length() > 17)
		{
			throw new IllegalArgumentException("Transaction id cannot be " + "longer than 17 characters.");
		}
		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();
		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("TRANSACTIONID", transactionId);
		nvpRequest.put("ACTION", action.toString());
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
		str.append("MangagePendingTransactionStatus class with the vlues: ");
		str.append("nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}
}
