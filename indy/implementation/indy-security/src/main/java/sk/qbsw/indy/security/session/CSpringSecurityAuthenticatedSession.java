package sk.qbsw.indy.security.session;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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
		for (GrantedAuthority authority : authentication.getAuthorities())
		{
			roles.add(authority.getAuthority());
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate (String username, String password)
	{
		boolean authenticated = false;
		try
		{
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			authenticated = authentication.isAuthenticated();
		}
		catch (AuthenticationException e)
		{
			LOGGER.warn(String.format("User '%s' failed to login. Reason: %s", username, e.getMessage()));
			authenticated = false;
		}
		return authenticated;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.AAuthenticatedSession#authenticate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate (String username, String password, String unit)
	{
		return authenticate(username, password);
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
}
