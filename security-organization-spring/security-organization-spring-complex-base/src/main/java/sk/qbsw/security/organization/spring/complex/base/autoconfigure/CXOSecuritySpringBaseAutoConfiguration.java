package sk.qbsw.security.organization.spring.complex.base.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.spring.complex.base.mapper.CXOUserDataMapperImpl;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;

/**
 * The complex organization security spring base auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
public class CXOSecuritySpringBaseAutoConfiguration
{
	public static final String USER_DATA_MAPPER_BEAN_NAME = "cxoUserDataMapper";

	@Bean
	@ConditionalOnMissingBean (name = USER_DATA_MAPPER_BEAN_NAME)
	public UserDataMapper<CXOUserOutputData> cxoUserDataMapper ()
	{
		return new CXOUserDataMapperImpl();
	}
}
