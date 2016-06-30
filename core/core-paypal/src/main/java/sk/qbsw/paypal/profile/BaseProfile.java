/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents paypal user - his/her password, user name etc.
 * BaseProfile builder for instatiating this class. Example:
 * BaseProfile usr = new BaseProfile.Builder(userName, pass).signature(sign).build();
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
public final class BaseProfile implements Profile
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** user name obtained from paypal. */
	private final String userName;

	/** password obtained from paypal. */
	private final String password;

	/** If you use an API certificate, do not include this parameter. */
	private final String signature;

	/** 
	 * Email address of a PayPal account that has granted you permission to 
	 * make this call.
	 * Set this parameter only if you are calling an API on a different user’s 
	 * behalf
	 */
	private final String subject;

	/**
	 * Builder class that constructs instance of BaseProfile class.
	 * 
	 * Example:
	 * BaseProfile usr = new BaseProfile.Builder(userName, pass).signature(sign).build();
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public static class Builder
	{

		/* required parameters */
		/** The user name. */
		private final String userName;
		
		/** The password. */
		private final String password;

		/* optional paramters */
		/** The signature. */
		private String signature = null;
		
		/** The subject. */
		private String subject = null;

		/**
		 * Required parameters.
		 *
		 * @param userName user name obtained from paypal
		 * @param password password obtained from paypal
		 */
		public Builder (String userName, String password)
		{
			this.userName = userName;
			this.password = password;
		}

		/**
		 * Optional parameter
		 * If you use an API certificate, do not include this parameter.
		 *
		 * @param val signature
		 * @return the builder
		 */
		public Builder signature (String val)
		{
			signature = val;
			return this;
		}

		/**
		 * Optional parameter
		 * Email address of a PayPal account that has granted you permission to
		 * make this call.
		 * Set this parameter only if you are calling an API on a different
		 * user’s behalf
		 *
		 * @param val subject
		 * @return the builder
		 */
		public Builder subject (String val)
		{
			subject = val;
			return this;
		}

		/**
		 * Returns instance of BaseProfile class.
		 *
		 * @return the base profile
		 */
		public BaseProfile build ()
		{
			return new BaseProfile(this);
		}
	}

	/**
	 * Private constructor invoked by builder static class.
	 *
	 * @param builder the builder
	 */
	private BaseProfile (Builder builder)
	{

		userName = builder.userName;
		password = builder.password;
		signature = builder.signature;
		subject = builder.subject;
	}

	/** 
	 * @see sk.qbsw.paypal.profile.Profile#getNVPMap()
	 */
	public Map<String, String> getNVPMap ()
	{

		/* create and return map */
		Map<String, String> nvpMap = new HashMap<String, String>();
		nvpMap.put("USER", userName);
		nvpMap.put("PWD", password);
		if (signature != null)
		{
			nvpMap.put("SIGNATURE", signature);
		}
		if (subject != null)
		{
			nvpMap.put("SUBJECT", subject);
		}

		return nvpMap;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{

		return "instance of User class with values: userName - " + userName + ", password: " + password + ", signature: " + signature + ", subject: " + subject;
	}
}
