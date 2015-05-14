package sk.qbsw.sgwt.winnetou.server.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import sk.qbsw.sgwt.winnetou.client.exception.CSystemFailureException;

/**
 * Server exception publisher passes
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CServerExceptionPublisher
{
	/**
	 * Returns stack trace of exception
	 * 
	 * @param ex
	 *            exception which stack trace is to be investigated
	 */
	public static String getStackTrace(Throwable ex)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		if (ex != null)
		{
			printWriter.write("Message :" + ex.getMessage() + " : ");
			ex.printStackTrace(printWriter);
		}
		printWriter.flush();
		return stringWriter.getBuffer().toString();
	}

	/**
	 * Passes exception as Runtime exception with additional message
	 * 
	 * @param message
	 * @param ex
	 */
	public static void pass(String message, Throwable ex)
	{
		throw new CSystemFailureException(message, ex);
	}

	/**
	 * Passes exception as Runtime exception
	 * 
	 * @param ex
	 */
	public static void pass(Throwable ex)
	{
		throw new CSystemFailureException(ex);
	}
}
