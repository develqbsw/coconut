package sk.qbsw.security.rest.oauth.api.mapper;

import org.springframework.stereotype.Component;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityOrikaMapperBase;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

import javax.annotation.PostConstruct;

/**
 * The security orika mapper.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Component
public class SecurityOrikaMapper extends SecurityOrikaMapperBase<AccountData, CSAccountData>
{
	/**
	 * Initialise the mapping.
	 */
	@PostConstruct
	protected void initMapping ()
	{
	}
}
