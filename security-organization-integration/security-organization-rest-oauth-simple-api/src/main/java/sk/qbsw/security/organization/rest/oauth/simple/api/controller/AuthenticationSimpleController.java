package sk.qbsw.security.organization.rest.oauth.simple.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.organization.rest.oauth.client.model.CSSimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.oauth.model.SimpleOrganizationAccountData;
import sk.qbsw.security.rest.oauth.api.base.controller.AuthenticationBaseController;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityMapper;

/**
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@RestController
public class AuthenticationSimpleController extends AuthenticationBaseController<SimpleOrganizationAccountData, CSSimpleOrganizationAccountData>
{
	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param oAuthService the o auth service
	 * @param securityMapper the security mapper
	 */
	@Autowired
	public AuthenticationSimpleController (OAuthServiceFacade<SimpleOrganizationAccountData> oAuthService, SecurityMapper securityMapper)
	{
		super(oAuthService, securityMapper);
	}
}
