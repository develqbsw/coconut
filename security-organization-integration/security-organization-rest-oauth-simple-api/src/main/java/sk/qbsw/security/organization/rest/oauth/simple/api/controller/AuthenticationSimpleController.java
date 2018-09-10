package sk.qbsw.security.organization.rest.oauth.simple.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.service.facade.SimpleOrganizationOAuthServiceFacade;
import sk.qbsw.security.rest.oauth.api.base.controller.AuthenticationControllerBase;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityMapper;

/**
 * The type Authentication simple controller.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@RestController
public class AuthenticationSimpleController extends AuthenticationControllerBase<SimpleOrganizationAccountData, CSSimpleOrganizationAccountData>
{
	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param oAuthService the o auth service
	 * @param securityMapper the security mapper
	 */
	@Autowired
	public AuthenticationSimpleController (SimpleOrganizationOAuthServiceFacade<SimpleOrganizationAccountData> oAuthService, SecurityMapper<SimpleOrganizationAccountData, CSSimpleOrganizationAccountData> securityMapper)
	{
		super(oAuthService, securityMapper);
	}
}
