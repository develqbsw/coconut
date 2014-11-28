package sk.qbsw.android.exception;

import sk.qbsw.android.CQBSWAndroidLoggerTag;
import android.util.Log;


/** 
 * class for logging exceptions
 * 
 * @author Dalibor Rak
 * @version 0.1.0
 * @since 0.1.0
 */
public class CExceptionHandler
{
	/** 
	 * method to throw exception
	 * 
	 * @param ex exception to throw
	 * @throws Exception
	 */
	public static void exceptionThrower (Exception ex) throws Exception
	{
		if (ex != null)
		{
			if (ex instanceof ArrayIndexOutOfBoundsException)
			{
				throw (ArrayIndexOutOfBoundsException) ex;
			}
			else if (ex instanceof ClassNotFoundException)
			{
				throw (ClassNotFoundException) ex;
			}
			else if (ex instanceof IndexOutOfBoundsException)
			{
				throw (IndexOutOfBoundsException) ex;
			}
			else if (ex instanceof NullPointerException)
			{
				throw (NullPointerException) ex;
			}
			else if (ex instanceof NumberFormatException)
			{
				throw (NumberFormatException) ex;
			}
			else if (ex instanceof RuntimeException)
			{
				throw (RuntimeException) ex;
			}
			else
			{
				throw (Exception) ex;
			}
		}
	}

	/** 
	 * method for standard exception logging to console
	 * 
	 * @param tag class for which are exeption logged(only for print message)
	 * @param th exception to print (only for print message)
	 */
	@SuppressWarnings ("rawtypes")
	public static void logException (Class tag, Throwable th)
	{
		Log.e(CQBSWAndroidLoggerTag.TAG, tag.getName()+" "+th.getMessage(), th);
	}
}