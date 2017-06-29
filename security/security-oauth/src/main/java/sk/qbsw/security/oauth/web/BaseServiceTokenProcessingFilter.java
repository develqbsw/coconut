package sk.qbsw.security.oauth.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.oauth.service.AuthenticationTokenService;
import sk.qbsw.security.oauth.service.MasterTokenService;
import sk.qbsw.security.web.filter.BaseTokenProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * The abstract token processing filter intended to use directly with service layer.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.13.0
 */
public abstract class BaseServiceTokenProcessingFilter extends BaseTokenProcessingFilter
{
	private final MasterTokenService masterTokenService;

	private final AuthenticationTokenService authenticationTokenService;

	public BaseServiceTokenProcessingFilter (AuthenticationManager authManager, MasterTokenService masterTokenService, AuthenticationTokenService authenticationTokenService)
	{
		super(authManager);
		this.masterTokenService = masterTokenService;
		this.authenticationTokenService = authenticationTokenService;
	}

	@Override
	public Authentication createAuthenticationToken (String token, String deviceId, String ip, HttpServletRequest request)
	{
		User user = findUser(token, deviceId, ip);

		final PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(user, token, null);
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return authenticationToken;
	}

	private User findUser (String token, String deviceId, String ip)
	{
		try
		{
			User userByMasterToken = masterTokenService.getUserByMasterToken(token, deviceId, ip);
			User userByAuthenticationToken = authenticationTokenService.getUserByAuthenticationToken(token, deviceId, ip);

			if (userByMasterToken != null)
			{
				return userByMasterToken;
			}
			else if (userByAuthenticationToken != null)
			{
				return userByAuthenticationToken;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			LOGGER.error("The exception in token processing filter", e);
			return null;
		}
	}
}
