/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;

/**
 * The Class ManageRecurringPaymentsProfileStatus.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class ManageRecurringPaymentsProfileStatus implements Request
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The action to be performed to the recurring payments profile.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum Action
	{
		/** Only profiles in Active or Suspended state can be cancelled. */
		CANCEL ("Cancel"),
		/** Only profiles in Active state can be suspended. */
		SUSPEND ("Suspend"),
		/** Only profiles in a suspended state can be reactivated. */
		REACTIVATE ("Reactivate");

		/** The value. */
		private String value;

		/**
		 * Instantiates a new action.
		 *
		 * @param value the value
		 */
		private Action (String value)
		{
			this.value = value;
		}

		/**
		 * Gets the value.
		 *
		 * @return value for name value pair request
		 */
		String getValue ()
		{
			return value;
		}
	}

	/** Method value of this request. */
	private static final String METHOD_NAME = "ManageRecurringPaymentsProfileStatus";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;

	/**
	 * Instantiates a new manage recurring payments profile status.
	 *
	 * @param profileId Recurring payments profile ID returned in the
	 * CreateRecurringPaymentsProfile response. Character
	 * length and limitations: 14 single-byte alphanumeric
	 * characters. 19 character profile IDs are supported for
	 * compatibility with previous versions of the PayPal API.
	 * @param action    The action to be performed to the recurring payments
	 * profile.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public ManageRecurringPaymentsProfileStatus (String profileId, Action action) throws IllegalArgumentException
	{

		if (profileId.length() != 14 || profileId.length() != 19)
		{
			throw new IllegalArgumentException("profileId has to be 14 or 19 " + "characters long");
		}

		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();
		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("PROFILEID", profileId);
		nvpRequest.put("ACTION", action.getValue());
	}

	/**
	 * Sets the note.
	 *
	 * @param note  The reason for the change in status. For profiles created
	 * using Express Checkout, this message will be included in
	 * the email notification to the buyer when the status of the
	 * profile is successfully changed, and can also be seen by
	 * both you and the buyer on the Status History page of the
	 * PayPal account.
	 */
	public void setNote (String note)
	{
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
		str.append("ManageRecurringPaymentsProfileStatus class with the ");
		str.append("values: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}
}
