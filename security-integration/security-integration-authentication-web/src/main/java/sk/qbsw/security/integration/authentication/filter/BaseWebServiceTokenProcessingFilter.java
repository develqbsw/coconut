package sk.qbsw.security.integration.authentication.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.request.VerifyRequestBody;
import sk.qbsw.security.integration.authentication.client.AuthenticationClient;
import sk.qbsw.security.web.filter.BaseTokenProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The abstract token processing filter intended to use with web service integration.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class BaseWebServiceTokenProcessingFilter extends BaseTokenProcessingFilter
{
	private final AuthenticationClient authenticationClient;

	public BaseWebServiceTokenProcessingFilter (AuthenticationManager authenticationManager, AuthenticationClient authenticationClient)
	{
		super(authenticationManager);
		this.authenticationClient = authenticationClient;
	}

	@Override
	public Authentication createAuthenticationToken (String token, String deviceId, String ip, HttpServletRequest request)
	{
		CSAccountData accountData = authenticationClient.verify(VerifyRequestBody.newBuilder().token(token).deviceId(deviceId).ip(ip).build());

		Collection<GrantedAuthority> grantedAuthorities = convertRolesToAuthorities(accountData.getRoles());
		final PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(accountData, token, grantedAuthorities);
		preAuthenticatedAuthenticationToken.setDetails(new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request, grantedAuthorities));

		return preAuthenticatedAuthenticationToken;
	}

	protected Collection<GrantedAuthority> convertRolesToAuthorities (List<String> roles)
	{
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (roles != null)
		{
			for (String role : roles)
			{
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}

		return grantedAuthorities;
	}
}
