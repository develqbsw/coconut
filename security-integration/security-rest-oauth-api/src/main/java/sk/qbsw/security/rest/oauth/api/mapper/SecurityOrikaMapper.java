package sk.qbsw.security.rest.oauth.api.mapper;

import org.springframework.stereotype.Component;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityOrikaBaseMapper;
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
public class SecurityOrikaMapper extends SecurityOrikaBaseMapper<CSAccountData>
{
	/**
	 * Initialise the mapping.
	 */
	@PostConstruct
	private void initMapping ()
	{

	}
}
