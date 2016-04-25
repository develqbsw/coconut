package sk.qbsw.core.communication.test.file;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import sk.qbsw.core.communication.file.EFileType;
import sk.qbsw.core.communication.file.sftp.CFileExchangerSFTP;

/**
 * The Class CSFTPVRPTestCase.
 */
public class CSFTPVRPConnectPerfTestCase {

	/** The fe. */
	private CFileExchangerSFTP fe = new CFileExchangerSFTP("chaos.qbsw.local", 22);

	/**
	 * Rule for concurency
	 */
	@Rule
	public ConcurrentRule rule = new ConcurrentRule();

	/**
	 * Rule for repeating
	 */
	@Rule
	public RepeatingRule rule2 = new RepeatingRule();

	/**
	 * Inits the connection.
	 */
	@Before
	public void initConnection() {
		try {
			JSchCommonsLogger jschLogger = new JSchCommonsLogger();
			com.jcraft.jsch.JSch.setLogger(jschLogger);
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@After
	public void release() {
		System.out.println("Test called: " + number);
	}

	private volatile int number = 0;

	/**
	 * Test ls file.
	 */
	@Test
	@Concurrent(count = 10)
	@Repeating(repetition = 10)
	public void testConnectDisconnect() {
		System.out.println("Connecting...");
		fe.setSimpleAuthentication("dev", "pkBAxo0sq0QJjD");
		fe.connect();
		System.out.println("Connected.");

		System.out.println("Disconnecting...");
		fe.disconnect();
		System.out.println("Disconnected.");

		number++;
	}
}
