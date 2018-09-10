package sk.qbsw.security.organization.rest.oauth.simple.api.mapper;

import org.springframework.stereotype.Component;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSimpleOrganizationAccountData;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSimplifiedOrganization;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationData;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityOrikaMapperBase;

import javax.annotation.PostConstruct;

/**
 * The type Security orika mapper.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@Component
public class SecurityOrikaMapper extends SecurityOrikaMapperBase<SimpleOrganizationAccountData, CSSimpleOrganizationAccountData>
{
	/**
	 * Initialise the mapping.
	 */
	@PostConstruct
	private void initMapping ()
	{
		mapperFactory.classMap(SimpleOrganizationAccountData.class, CSSimpleOrganizationAccountData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(SimpleOrganizationData.class, CSSimplifiedOrganization.class) //
			.byDefault() //
			.register();

	}
}
