package sk.qbsw.core.security.oauth.service.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.security.oauth.service.IIdGeneratorService;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.NameBasedGenerator;

/**
 * The Class CIdGeneratorService.
 *
 * @author meszaros
 */
@Service
@CLogged
public class CIdGeneratorService implements IIdGeneratorService
{
	/** The log. */
	private Logger log = LoggerFactory.getLogger(getClass());

	/** The ns uuid. */
	private UUID nsUuid = NameBasedGenerator.NAMESPACE_URL;

	/** The g. */
	private NameBasedGenerator g;

	/** The mac string. */
	private String macString;

	/**
	 * Instantiates a new c id generator service.
	 *
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public CIdGeneratorService () throws NoSuchAlgorithmException
	{
		InetAddress ip;
		try
		{

			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++)
			{
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			macString = sb.toString();

		}
		catch (Exception e)
		{
			log.warn("nepodarilo sa ziskat mac adresu pre generovanie id", e);
		}
		finally
		{
			if (macString == null)
			{
				macString = String.valueOf(System.nanoTime());
			}
		}
		g = Generators.nameBasedGenerator(nsUuid, MessageDigest.getInstance("SHA-256"));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IIdGeneratorService#getGeneratedId(java.lang.String)
	 */
	@Override
	public String getGeneratedId (String s)
	{
		return generateId(s);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IIdGeneratorService#getGeneratedId()
	 */
	@Override
	public String getGeneratedId ()
	{
		return generateId(macString);

	}

	/**
	 * Generate id.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String generateId (String s)
	{
		UUID uuid = g.generate(String.valueOf(System.nanoTime()) + s);
		String id = uuid.toString().replaceAll("-", "").toUpperCase();
		log.debug("generated id: " + id);
		return id;
	}
}
