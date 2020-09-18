package sk.qbsw.security.organization.spring.simple.base.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.organization.simple.base.model.SPOUserOutputData;
import sk.qbsw.security.organization.spring.simple.base.mapper.SPOUserDataMapperImpl;
import sk.qbsw.security.spring.base.mapper.UserDataMapperBase;

/**
 * The simple organization security spring base auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
public class SPOSecuritySpringBaseAutoConfiguration
{
	public static final String USER_DATA_MAPPER_BEAN_NAME = "spoUserDataMapper";

	@Bean
	@ConditionalOnMissingBean (name = USER_DATA_MAPPER_BEAN_NAME)
	public UserDataMapperBase<SPOUserOutputData> spoUserDataMapper ()
	{
		return new SPOUserDataMapperImpl();
	}
}
