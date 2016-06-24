package sk.qbsw.core.communication.file.sftp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.file.EFileType;
import sk.qbsw.core.communication.file.IFileExchanger;
import sk.qbsw.core.communication.file.IFileExchangerAuthenticator;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * File Exchanger using SFTP
 * 
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public class CFileExchangerSFTP implements IFileExchanger, IFileExchangerAuthenticator
{

	/** The jsch library object. */
	private JSch jsch = new JSch();

	/** The session. */
	private Session session = null;

	/** The sftp channel. */
	private ChannelSftp sftpChannel;

	/** The url. */
	private String url;

	/** The port. */
	private int port;

	/** The login. */
	private String login;

	/** The password. */
	private String password;

	/**
	 * Instantiates a new c file exchanger sftp with specific settings.
	 *
	 * @param url the url to SFTP server
	 * @param port the port port for SFTP server
	 */
	public CFileExchangerSFTP (String url, int port)
	{
		this.url = url;
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#ls(java.lang.String, sk.qbsw.core.communication.file.EFileType)
	 */
	@Override
	public List<String> ls (String path, EFileType fileType)
	{
		try
		{
			List<String> retVal = new ArrayList<String>();

			@SuppressWarnings ("unchecked")
			Vector<ChannelSftp.LsEntry> vals = sftpChannel.ls(path);
			for (ChannelSftp.LsEntry object : vals)
			{
				switch (fileType)
				{
					case DIRECTORY:
						if (object.getAttrs().isDir())
						{
							retVal.add(object.getFilename());
						}
						break;
					case FILE:
						if (!object.getAttrs().isDir())
						{
							retVal.add(object.getFilename());
						}
						break;
					default:
						retVal.add(object.getFilename());
						break;
				}

				// file is directory

			}

			return retVal;
		}
		catch (SftpException ex)
		{
			throw new CSystemException("Ls unsuccesfull", ex);
		}

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#get(java.lang.String)
	 */
	@Override
	public InputStream get (String path)
	{
		try
		{
			return sftpChannel.get(path);
		}
		catch (SftpException ex)
		{
			throw new CSystemException("File download unsuccesfull", ex);
		}

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#put(java.lang.String, java.io.InputStream)
	 */
	@Override
	public void put (String dstPath, InputStream fileToTransfer)
	{
		try
		{
			sftpChannel.put(fileToTransfer, dstPath);
		}
		catch (SftpException ex)
		{
			throw new CSystemException("File upload unsuccesfull", ex);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#connect()
	 */
	public synchronized void connect ()
	{
		try
		{
			this.session = this.jsch.getSession(login, url, port);
			this.session.setConfig("StrictHostKeyChecking", "no");
			this.session.setPassword(password);
			this.session.connect();

			Channel channel = this.session.openChannel("sftp");
			channel.connect();
			this.sftpChannel = (ChannelSftp) channel;

		}
		catch (JSchException ex)
		{
			throw new CSystemException("Connection not initialized", ex);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#disconnect()
	 */
	public synchronized void disconnect ()
	{
		if (this.session != null)
		{
			session.disconnect();
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchangerAuthenticator#setSimpleAuthentication(java.lang.String, java.lang.String)
	 */
	@Override
	public void setSimpleAuthentication (String login, String password)
	{
		this.login = login;
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchangerAuthenticator#setCertificateAuthentication(java.lang.String)
	 */
	@Override
	public void setCertificateAuthentication (String pathTocertificate)
	{
		throw new UnsupportedOperationException("Method not implemented");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#remove(java.lang.String)
	 */
	@Override
	public void remove (String path)
	{
		try
		{
			this.sftpChannel.rm(path);

		}
		catch (SftpException ex)
		{
			throw new CSystemException("File remove problem", ex);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.file.IFileExchanger#rename(java.lang.String, java.lang.String)
	 */
	@Override
	public void rename (String srcPath, String destPat)
	{
		try
		{
			this.sftpChannel.rename(srcPath, destPat);

		}
		catch (SftpException ex)
		{
			throw new CSystemException("File rename problem", ex);
		}
	}
}
