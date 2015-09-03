package sk.qbsw.core.security.oauth.service.impl;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.security.oauth.service.IIdGeneratorService;

/**
 * Generatof for ID using secured random implementation.
 *
 * @author Dalibor Rak
 * @version 1.13.3
 * @since 1.13.3
 */
@CLogged
@Service ("randomGenerator")
public class CRandomIdGeneratorService extends AIdGeneratorService implements IIdGeneratorService
{
	/**  The generator. */
	private RandomBasedGenerator g;

	/** The random. */
	private Random random;

	/**
	* Instantiates a new c random id generator.
	*/
	public CRandomIdGeneratorService ()
	{
		random = new SecureRandom();
		g = Generators.randomBasedGenerator(random);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IIdGeneratorService#getGeneratedId(java.lang.String)
	 */
	@Override
	public String getGeneratedId (String s)
	{
		return getGeneratedId();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.service.IIdGeneratorService#getGeneratedId()
	 */
	@Override
	public String getGeneratedId ()
	{
		String id = clearUID(g.generate());
		return id;
	}
}
