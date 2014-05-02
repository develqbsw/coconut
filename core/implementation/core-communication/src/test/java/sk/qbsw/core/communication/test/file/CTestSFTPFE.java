package sk.qbsw.core.communication.test.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.qbsw.core.communication.file.EFileType;
import sk.qbsw.core.communication.file.sftp.CFileExchangerSFTP;

public class CTestSFTPFE
{

	private CFileExchangerSFTP fe = new CFileExchangerSFTP("talos.qbsw.local", 22);

	@Before
	public void initConnection ()
	{
		System.out.println("Connecting...");
		fe.setSimpleAuthentication("pep", "BCYzwiDdyJ1n70X");
		fe.connect();
		System.out.println("Connected.");
	}

	@Test
	public void testLsFile ()
	{
		System.out.println("Listing files:");
		List<String> content = fe.ls("./pep", EFileType.FILE);
		for (String val : content)
		{
			System.out.println(val);
		}
	}

	@Test
	public void testLsDir ()
	{
		System.out.println("Listing dir:");
		List<String> content = fe.ls("./pep", EFileType.DIRECTORY);
		for (String val : content)
		{
			System.out.println(val);
		}
	}

	@Test
	public void testLsAny ()
	{
		System.out.println("Listing any:");
		List<String> content = fe.ls("./pep", EFileType.ANY);
		for (String val : content)
		{
			System.out.println(val);
		}
	}

	@Test
	public void testPut () throws FileNotFoundException
	{
		System.out.println("Uploading...");
		fe.put("./pep/1.wsdl", new FileInputStream("/Users/rak/Temp/1.wsdl"));
		System.out.println("Uploaded.");
	}

	@Test
	public void testGet () throws IOException
	{
		System.out.println("Downloading...");
		IOUtils.copy(fe.get("./pep/1.wsdl"), System.out);
		System.out.println("Downloaded.");
	}

	@Test
	public void testRename () throws FileNotFoundException
	{
		System.out.println("Renaming...");
		fe.rename("./pep/1.wsdl", "./pep/incoming/2.wsdl");
		System.out.println("Renamed.");
	}

	@Test
	public void testRemove () throws FileNotFoundException
	{
		System.out.println("Removing...");
		fe.remove("./pep/incoming/2.wsdl");
		System.out.println("Removed.");
	}

	
	@After
	public void closeConnections ()
	{
		System.out.println("Disconnecting...");
		fe.disconnect();
		System.out.println("Disconnected.");
		System.out.println("------------------");
	}

}
