package sk.qbsw.security.organization.rest.oauth.complex.api.mapper;

import org.springframework.stereotype.Component;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.model.OrganizationData;
import sk.qbsw.security.organization.complex.oauth.model.UnitData;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSComplexOrganizationAccountData;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSSimplifiedOrganization;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSSimplifiedUnit;
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
public class SecurityOrikaMapper extends SecurityOrikaMapperBase<ComplexOrganizationAccountData, CSComplexOrganizationAccountData>
{
	/**
	 * Initialise the mapping.
	 */
	@PostConstruct
	private void initMapping ()
	{
		mapperFactory.classMap(ComplexOrganizationAccountData.class, CSComplexOrganizationAccountData.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(OrganizationData.class, CSSimplifiedOrganization.class) //
			.byDefault() //
			.register();
		mapperFactory.classMap(UnitData.class, CSSimplifiedUnit.class) //
			.byDefault() //
			.register();

	}
}