/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package sk.qbsw.indy.security.session;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.request.Request;

import sk.qbsw.security.authentication.model.AuthenticationSecurityToken;


/**
 * Basic authenticated web session. Subclasses must provide a method that authenticates the session
 * based on a username and password, and a method implementation that gets the Roles
 * 
 * @author Jonathan Locke
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.6.0
 */
public abstract class AAuthenticatedWebSession extends AbstractAuthenticatedWebSession
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** True when the user is signed in */
	private volatile boolean signedIn;

	/**
	 * @return Current authenticated web session
	 */
	public static AAuthenticatedWebSession get ()
	{
		return (AAuthenticatedWebSession) Session.get();
	}

	/**
	 * Construct.
	 * 
	 * @param request
	 *            The current request object
	 */
	public AAuthenticatedWebSession (Request request)
	{
		super(request);
	}

	/**
	 * Authentication checks using authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @return true, if successful
	 */
	public abstract boolean authenticate (final AuthenticationSecurityToken authenticationToken);

	/**
	 * Call signOut() and delete the logon data from where ever they have been persisted (e.g.
	 * Cookies)
	 */
	@Override
	public void invalidate ()
	{
		signOut();
		super.invalidate();
	}

	/**
	 * @return true, if user is signed in
	 */
	@Override
	public final boolean isSignedIn ()
	{
		return signedIn;
	}

	/**
	 * Cookie based logins (remember me) may not rely on putting username and password into the
	 * cookie but something else that safely identifies the user. This method is meant to support
	 * these use cases.
	 * 
	 * It is protected (and not public) to enforce that cookie based authentication gets implemented
	 * in a subclass (like you need to implement {@link #authenticate(String, String)} for 'normal'
	 * authentication).
	 * 
	 * @see #authenticate(String, String)
	 * 
	 * @param value
	 */
	protected final void signIn (boolean value)
	{
		signedIn = value;
	}

	/**
	 * Try to logon the user.
	 *
	 * @param authenticationToken the authentication token
	 * @return true, if logon was successful
	 */
	public final boolean signIn (final AuthenticationSecurityToken authenticationToken)
	{
		signedIn = authenticate(authenticationToken);
		if (signedIn)
		{
			bind();
		}
		return signedIn;
	}

	/**
	 * Sign the user out.
	 */
	public void signOut ()
	{
		signedIn = false;
	}
}
