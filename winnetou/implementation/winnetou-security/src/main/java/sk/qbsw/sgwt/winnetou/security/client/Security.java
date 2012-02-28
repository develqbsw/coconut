package sk.qbsw.sgwt.winnetou.security.client;

import sk.qbsw.sgwt.winnetou.security.client.ui.localization.CClientExceptionMessages;

import com.google.gwt.core.client.EntryPoint;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Security implements EntryPoint
{

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad ()
	{
		CClientExceptionMessages.initializeMessages();
	}
}
