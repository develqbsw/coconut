package sk.qbsw.security.organization.rest.oauth.complex.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sk.qbsw.security.organization.complex.oauth.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.oauth.service.facade.ComplexOrganizationOAuthServiceFacade;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSComplexOrganizationAccountData;
import sk.qbsw.security.rest.oauth.api.base.controller.AuthenticationControllerBase;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityMapper;

/**
 * The type Authentication complex controller.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@RestController
public class AuthenticationComplexController extends AuthenticationControllerBase<ComplexOrganizationAccountData, CSComplexOrganizationAccountData>
{
	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param oAuthService   the o auth service
	 * @param securityMapper the security mapper
	 */
	@Autowired
	public AuthenticationComplexController (ComplexOrganizationOAuthServiceFacade<ComplexOrganizationAccountData> oAuthService, SecurityMapper<ComplexOrganizationAccountData, CSComplexOrganizationAccountData> securityMapper)
	{
		super(oAuthService, securityMapper);
	}
}
