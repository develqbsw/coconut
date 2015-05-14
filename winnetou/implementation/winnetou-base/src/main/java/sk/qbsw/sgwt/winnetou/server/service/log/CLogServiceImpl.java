package sk.qbsw.sgwt.winnetou.server.service.log;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sk.qbsw.sgwt.winnetou.client.service.log.ILogService;

/**
 * The server side implementation of the RPC service.
 * 
 * @see ILogService
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
@Service(value = "logServiceImpl")
public class CLogServiceImpl implements ILogService
{

	/**
	 * @see ILogService#debug(String)
	 */
	public void debug(String message)
	{
		Logger.getLogger(CLogServiceImpl.class).debug(message);
	}

	/**
	 * @see ILogService#error(String)
	 */
	public void error(String message)
	{
		Logger.getLogger(CLogServiceImpl.class).error(message);
	}

	/**
	 * @see ILogService#info(String)
	 */
	public void info(String message)
	{
		Logger.getLogger(CLogServiceImpl.class).info(message);
	}
}
