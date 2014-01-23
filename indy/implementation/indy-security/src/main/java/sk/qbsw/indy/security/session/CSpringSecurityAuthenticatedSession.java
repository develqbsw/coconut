package sk.qbsw.indy.security.session;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.spring.CUsernamePasswordUnitAuthenticationToken;

/**
 * Authentication for Wicket using Spring security.
 *
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public class CSpringSecurityAuthenticatedSession extends AAuthenticatedSecurityWebSession
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CSpringSecurityAuthenticatedSession.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The authentication manager. */
	@SpringBean (name = "authenticationManager")
	private AuthenticationManager authenticationManager;

	/**
	 * Instantiates a new c spring ws authenticated session.
	 *
	 * @param request the request
	 */
	public CSpringSecurityAuthenticatedSession (Request request)
	{
		super(request);
		injectDependencies();
	}

	/**
	 * Adds the roles from authentication.
	 *
	 * @param roles the roles
	 * @param authentication the authentication
	 */
	private void addRolesFromAuthentication (Roles roles, Authentication authentication)
	{
		if (authentication != null && authentication.getAuthorities() != null)
		{
			for (GrantedAuthority authority : authentication.getAuthorities())
			{
				roles.add(authority.getAuthority());
			}
		}
	}

	/**
	 * Authenticate.
	 *
	 * @param authenticationToken the authentication token
	 * @return true, if successful
	 */
	private Authentication authenticate (AbstractAuthenticationToken authenticationToken)
	{
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate (String username, String password)
	{
		return authenticate(username, password, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.AAuthenticatedSession#authenticate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate (String username, String password, String unit)
	{
		try
		{
			CUsernamePasswordUnitAuthenticationToken token = (CUsernamePasswordUnitAuthenticationToken) authenticate(new CUsernamePasswordUnitAuthenticationToken(username, password, unit));
			boolean authenticated = token.isAuthenticated();

			if (authenticated)
			{
				prepareSessionData(token);
			}
			return authenticated;

		}
		catch (AuthenticationException e)
		{
			LOGGER.warn(String.format("User '%s' unit %s failed to login. Reason: %s", username, unit, e.getMessage()));
			setSecurityException(new CSecurityException(e.getMessage()));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession#getRoles()
	 */
	@Override
	public Roles getRoles ()
	{
		Roles roles = new Roles();

		if (isSignedIn())
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			addRolesFromAuthentication(roles, authentication);
		}
		return roles;
	}

	/**
	 * Inject dependencies.
	 */
	private void injectDependencies ()
	{
		Injector.get().inject(this);

		if (authenticationManager == null)
		{
			throw new IllegalStateException("An authenticationManager is required.");
		}
	}

	/**
	 * Prepare session data.
	 *
	 * @param token the token
	 */
	private void prepareSessionData (CUsernamePasswordUnitAuthenticationToken token)
	{
		CUser user = (CUser) token.getDetails();
		String unit = token.getUnit();

		super.setUser(user);
		super.setOrganization(user.getOrganization());
		super.setUnit(unit);
	}

}
