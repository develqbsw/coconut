package sk.qbsw.sgwt.winnetou.server.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Class reads HttpSession from thread locale. It uses
 * org.springframework.web.context.request.RequestContextListener from spring
 * library (it must be part of web.xml)
 * 
 * @author Dalibor Rak
 * @version 0.1
 * 
 */
public class CServletSessionUtils
{
	/**
	 * Reads Http session from Thread locale using spring library
	 * 
	 * @return HttpSession
	 */
	public static HttpServletRequest getHttpRequest()
	{
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) ra).getRequest();

	}

	/**
	 * Reads Http session from Thread locale using spring library
	 * 
	 * @return HttpSession
	 */
	public static HttpSession getHttpSession()
	{
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpSession session = ((ServletRequestAttributes) ra).getRequest().getSession();
		return session;
	}

	/**
	 * Gets locale from the request context
	 * 
	 * @return actual locale
	 */
	public static Locale getLocale()
	{
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) ra).getRequest().getLocale();
	}

}
