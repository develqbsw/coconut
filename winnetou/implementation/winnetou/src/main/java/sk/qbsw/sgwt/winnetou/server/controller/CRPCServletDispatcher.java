package sk.qbsw.sgwt.winnetou.server.controller;

import org.apache.log4j.Logger;
import org.gwtrpcspring.RemoteServiceDispatcher;

import sk.qbsw.sgwt.winnetou.server.exception.CServerExceptionPublisher;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;

/**
 * Default handler of all rpc calls, uses RemoteServiceDispatcher
 * 
 * @author Dalibor Rak
 * @version 0.1
 */
public class CRPCServletDispatcher extends RemoteServiceDispatcher
{
	/**
	 * Default version
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = null;

	public CRPCServletDispatcher()
	{
		super();
		logger = Logger.getLogger(CRPCServletDispatcher.class);
		if (logger != null)
		{
			logger.debug("Getting servlet dispatcher instance");
		}
	}

	@Override
	public String processCall(String payload) throws SerializationException
	{
		try
		{
			if (logger != null)
			{
				logger.debug("Servlet dispatcher start \n" + payload);
			}
			return super.processCall(payload);
		} catch (Throwable ex)
		{
			String exception = CServerExceptionPublisher.getStackTrace(ex);
			if (logger != null)
			{
				logger.error(exception);
			}
			return RPC.encodeResponseForFailure(null, ex);
		} finally
		{
			if (logger != null)
			{
				logger.debug("Servlet dispatcher finish");
			}
		}
	}
}
