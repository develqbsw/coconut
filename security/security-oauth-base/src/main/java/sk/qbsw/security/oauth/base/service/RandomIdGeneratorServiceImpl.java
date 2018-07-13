package sk.qbsw.security.oauth.base.service;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import sk.qbsw.core.base.logging.annotation.CLogged;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Generator for ID using secured random implementation.
 *
 * @author Dalibor Rak
 * @version 1.13.3
 * @since 1.13.3
 */
@CLogged
public class RandomIdGeneratorServiceImpl extends IdGeneratorServiceBase implements IdGeneratorService
{
	private RandomBasedGenerator g;

	private Random random;

	public RandomIdGeneratorServiceImpl ()
	{
		random = new SecureRandom();
		g = Generators.randomBasedGenerator(random);
	}

	@Override
	public String getGeneratedId (String s)
	{
		return getGeneratedId();
	}

	@Override
	public String getGeneratedId ()
	{
		return clearUID(g.generate());
	}
}
