package sk.qbsw.sgwt.winnetou.security.client;

import com.google.gwt.core.client.EntryPoint;

import sk.qbsw.sgwt.winnetou.security.client.ui.localization.CClientExceptionMessages;


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
