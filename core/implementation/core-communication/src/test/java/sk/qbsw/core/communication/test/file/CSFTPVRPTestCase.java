package sk.qbsw.core.communication.test.file;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jcraft.jsch.Logger;

import sk.qbsw.core.communication.file.EFileType;
import sk.qbsw.core.communication.file.sftp.CFileExchangerSFTP;

/**
 * The Class CSFTPVRPTestCase.
 */
@Ignore
public class CSFTPVRPTestCase
{

	/** The fe. */
	private CFileExchangerSFTP fe = new CFileExchangerSFTP("chaos.qbsw.local", 22);

	/**
	 * Inits the connection.
	 */
	@Before
	public void initConnection()
	{
		try {
			JSchCommonsLogger jschLogger = new JSchCommonsLogger();
			com.jcraft.jsch.JSch.setLogger(jschLogger);

			System.out.println("Connecting...");
			fe.setSimpleAuthentication("dev", "pkBAxo0sq0QJjD");
			fe.connect();
			System.out.println("Connected.");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Test ls file.
	 */
	@Test
	public void testLsFile()
	{
		System.out.println("Listing files:");
		List<String> content = fe.ls(".", EFileType.FILE);
		for (String val : content)
		{
			System.out.println(val);
		}
	}

	/**
	 * Test ls dir.
	 */
	@Test
	public void testLsDir()
	{
		System.out.println("Listing dir:");
		List<String> content = fe.ls("./", EFileType.DIRECTORY);
		for (String val : content)
		{
			System.out.println(val);
		}
	}

	/**
	 * Close connections.
	 */
	@After
	public void closeConnections()
	{
		System.out.println("Disconnecting...");
		fe.disconnect();
		System.out.println("Disconnected.");
		System.out.println("------------------");
	}
}

/**
 * Class to route log messages generated by JSch to Apache Commons Logging.
 * 
 * @see com.jcraft.jsch.Logger
 */
class JSchCommonsLogger implements Logger {

	private Log log;

	/**
	 * Constructor with custom category name 
	 * 
	 * @param logName the category name used by this logger for Apache Commons.
	 */
	public JSchCommonsLogger(String logName) {
		log = LogFactory.getLog(logName);
	}

	/**
	 * Default constructor
	 */
	public JSchCommonsLogger() {
		this(Logger.class.getName());
	}

	/* (non-Javadoc)
	 * @see com.jcraft.jsch.Logger#isEnabled(int)
	 */
	public boolean isEnabled(int level) {
		switch (level) {
			case DEBUG:
				return log.isDebugEnabled();
			case INFO:
				return log.isInfoEnabled();
			case WARN:
				return log.isWarnEnabled();
			case ERROR:
				return log.isErrorEnabled();
			case FATAL:
				return log.isFatalEnabled();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.jcraft.jsch.Logger#log(int, java.lang.String)
	 */
	public void log(int level, String message) {
		switch (level) {
			case DEBUG:
				log.debug(message);
				break;
			case INFO:
				log.info(message);
				break;
			case WARN:
				log.warn(message);
				break;
			case ERROR:
				log.error(message);
				break;
			case FATAL:
				log.fatal(message);
				break;
		}
	}

}
