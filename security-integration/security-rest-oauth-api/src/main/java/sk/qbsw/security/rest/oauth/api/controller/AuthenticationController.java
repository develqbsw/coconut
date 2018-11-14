package sk.qbsw.security.rest.oauth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.oauth.service.facade.OAuthServiceFacade;
import sk.qbsw.security.rest.oauth.api.base.controller.AuthenticationControllerBase;
import sk.qbsw.security.rest.oauth.api.base.mapper.SecurityMapper;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;

/**
 * The type Authentication controller.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@RestController
public class AuthenticationController extends AuthenticationControllerBase<AccountData, CSAccountData>
{
	/**
	 * Instantiates a new Authentication controller.
	 *
	 * @param oAuthService the o auth service
	 * @param securityMapper the security mapper
	 */
	@Autowired
	public AuthenticationController (OAuthServiceFacade<AccountData> oAuthService, SecurityMapper<AccountData, CSAccountData> securityMapper)
	{
		super(oAuthService, securityMapper);
	}
}
