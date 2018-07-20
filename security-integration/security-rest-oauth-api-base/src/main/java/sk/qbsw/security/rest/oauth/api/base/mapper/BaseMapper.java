package sk.qbsw.security.rest.oauth.api.base.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import ma.glasnost.orika.MapperFactory;

/**
 * The base orika authenticationTokenMapper.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public abstract class BaseMapper
{
	/** The authenticationTokenMapper factory. */
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
