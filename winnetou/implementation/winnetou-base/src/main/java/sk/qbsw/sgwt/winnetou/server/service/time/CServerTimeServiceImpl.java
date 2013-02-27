package sk.qbsw.sgwt.winnetou.server.service.time;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import sk.qbsw.sgwt.winnetou.client.service.time.IServerTimeService;

/**
 * Basic implementation of time service
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
@Service(value = "timeService")
public class CServerTimeServiceImpl implements IServerTimeService
{

	/**
	 * @see IServerTimeService#getServerTime()
	 */
	public Date getServerTime()
	{
		return Calendar.getInstance().getTime();
	}

}
