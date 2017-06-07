package sk.qbsw.security.integration.authentication.mapping;

import org.springframework.beans.factory.annotation.Autowired;

import ma.glasnost.orika.MapperFactory;

/**
 * The base orika mapper.
 * 
 * @author Tomas Lauro
 *
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class BaseMapper
{
	/** The mapper factory. */
	@Autowired
	protected MapperFactory mapperFactory;

	/**
	 * @return the mapperFactory
	 */
	public MapperFactory getMapperFactory ()
	{
		return mapperFactory;
	}
}
