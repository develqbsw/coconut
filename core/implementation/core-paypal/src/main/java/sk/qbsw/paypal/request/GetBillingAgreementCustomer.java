/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;

/**
 * GetBillingAgreementCustomerDetails Request Message.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>.
 * @author Dalibor Rak
 */
public final class GetBillingAgreementCustomer implements Request
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Method value of this request. */
	private static final String METHOD_NAME = "GetBillingAgreementCustomerDetails";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;


	/**
	 * Token cannot be null and cannot exceed 20 characters, otherwise
	 * exception is thrown.
	 *
	 * @param token The time-stamped token returned in the
	 * SetCustomerBillingAgreement response.
	 * NOTE:The token expires after three hours.
	 * Character length and limitations: 20 single-byte characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public GetBillingAgreementCustomer (String token) throws IllegalArgumentException
	{

		/* validation */
		if (token == null || token.length() > 20)
		{
			throw new IllegalArgumentException("token cannot be null and " + "cannot be longer than 20 characters");
		}

		/* set instance variables */
		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("TOKEN", token);
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
