package sk.qbsw.indy.security.session;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authentication.model.AuthenticationSecurityToken;

/**
 * Authentication for Wicket using Spring security.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public abstract class ASpringSecurityAuthenticatedSession extends AAuthenticatedSecurityWebSession
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ASpringSecurityAuthenticatedSession.class);

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The authentication manager. */
	@SpringBean (name = "authenticationManager")
	private transient AuthenticationManager authenticationManager;

	/**
	 * Instantiates a new c spring ws authenticated session.
	 *
	 * @param request the request
	 */
	public ASpringSecurityAuthenticatedSession (Request request)
	{
		super(request);
		injectDependencies();
	}

	/**
	 * Gets the.
	 *
	 * @return the a spring security authenticated session
	 */
	public static ASpringSecurityAuthenticatedSession get ()
	{
		return (ASpringSecurityAuthenticatedSession) AAuthenticatedWebSession.get();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.indy.security.session.AAuthenticatedWebSession#authenticate(sk.qbsw.core.security.model.IAuthenticationToken)
	 */
	@Override
	public boolean authenticate (AuthenticationSecurityToken authenticationToken)
	{
		//read authentication and checks if it is supported
		Authentication authentication = transformAuthenticationObject(authenticationToken);
		if (authentication == null)
		{
			LOGGER.warn("The given authentication token is not supported");
			setSecurityException(new CSecurityException("The given authentication token is not supported"));
			return false;
		}


		try
		{
			Authentication populatedAuthentication = authenticate(authentication);
			boolean authenticated = populatedAuthentication.isAuthenticated();

			if (authenticated && !prepareSessionData(populatedAuthentication))
			{
				LOGGER.warn("The data population failed - maybe the given authentication token is not supported");
				setSecurityException(new CSecurityException("The data population failed - maybe the given authentication token is not supported"));
				return false;
			}

			return authenticated;

		}
		catch (AuthenticationException e)
		{
			LOGGER.warn(String.format("User '%s' failed to login. Reason: %s", String.valueOf(authenticationToken.getPrincipal()), e));
			setSecurityException(new CSecurityException(e.getMessage()));
		}
		return false;
	}

	/**
	 * Authenticate given token.
	 *
	 * @param authenticationToken the authentication token
	 * @return authentication token if successful
	 */
	private Authentication authenticate (Authentication authenticationToken)
	{
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
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
	 * Transform the authentication object from authentication token. Checks if the token is supported and return null if it is not.
	 *
	 * @param authenticationToken the authentication token
	 * @return the authentication object or null if the given token is not supported
	 */
	protected abstract Authentication transformAuthenticationObject (AuthenticationSecurityToken authenticationToken);

	/**
	 * Prepare session data from given authentication object. If the give authentication object is not supported return false.
	 *
	 * @param authentication the authentication
	 * @return true, if successful, false if not supported or failed
	 */
	protected abstract boolean prepareSessionData (Authentication authentication);
}
