package sk.qbsw.security.spring.base.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.base.mapper.UserDataMapperImpl;

/**
 * The security spring base auto configuration.
 *
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 2.6.0
 */
@Configuration
public class SecuritySpringBaseAutoConfiguration
{
	public static final String USER_DATA_MAPPER_BEAN_NAME = "userDataMapper";

	@Bean
	@ConditionalOnMissingBean (name = USER_DATA_MAPPER_BEAN_NAME)
	public UserDataMapper<UserOutputData> userDataMapper ()
	{
		return new UserDataMapperImpl();
	}
}
