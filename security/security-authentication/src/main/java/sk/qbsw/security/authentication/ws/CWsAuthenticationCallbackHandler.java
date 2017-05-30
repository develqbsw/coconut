package sk.qbsw.security.authentication.ws;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ws.soap.security.callback.AbstractCallbackHandler;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;

import sk.qbsw.security.authentication.model.spring.CUsernamePasswordUnitAuthentication;

/**
 * The callback handler for WS authentication.
 * 
 * @author Tomas Lauro
 * @version 1.6.1
 * @since 1.6.1
 */
public class CWsAuthenticationCallbackHandler extends AbstractCallbackHandler implements InitializingBean
{
	/** The authentication manager. */
	@Qualifier ("authenticationManager")
	@Autowired
	private AuthenticationManager authenticationManager;

	/* (non-Javadoc)
	 * @see org.springframework.ws.soap.security.callback.AbstractCallbackHandler#handleInternal(javax.security.auth.callback.Callback)
	 */
	@Override
	protected void handleInternal (final Callback callback) throws IOException, UnsupportedCallbackException
	{
		//if the callback is passwordValidationCallback
		if (callback instanceof PasswordValidationCallback)
		{
			//create callback variable
			final PasswordValidationCallback passwordCallback = (PasswordValidationCallback) callback;

			if (passwordCallback.getRequest() instanceof PasswordValidationCallback.DigestPasswordRequest)
			{
				passwordCallback.setValidator(new CDigestPasswordValidator());
			}
			else if (passwordCallback.getRequest() instanceof PasswordValidationCallback.PlainTextPasswordRequest)
			{
				passwordCallback.setValidator(new CPlainTextPasswordValidator());
			}
			else
			{
				throw new UnsupportedCallbackException(callback);
			}
		}
	}

	/**
	 * Encrypted password validator.
	 * 
	 * @author Tomas Lauro
	 * @version 1.6.1
	 * @since 1.6.1
	 */
	private class CDigestPasswordValidator implements PasswordValidationCallback.PasswordValidator
	{
		/* (non-Javadoc)
		 * @see com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidator#validate(com.sun.xml.wss.impl.callback.PasswordValidationCallback.Request)
		 */
		@Override
		public boolean validate (final PasswordValidationCallback.Request request) throws PasswordValidationCallback.PasswordValidationException
		{
			final PasswordValidationCallback.DigestPasswordRequest digestPasswordRequest = (PasswordValidationCallback.DigestPasswordRequest) request;
			final String username = digestPasswordRequest.getUsername();
			final String password = digestPasswordRequest.getPassword();

			return authenticate(username, password);
		}
	}

	/**
	 * Plain text password validator.
	 * 
	 * @author Tomas Lauro
	 * @version 1.6.1
	 * @since 1.6.1
	 */
	private class CPlainTextPasswordValidator implements PasswordValidationCallback.PasswordValidator
	{
		/* (non-Javadoc)
		 * @see com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidator#validate(com.sun.xml.wss.impl.callback.PasswordValidationCallback.Request)
		 */
		@Override
		public boolean validate (final PasswordValidationCallback.Request request) throws PasswordValidationCallback.PasswordValidationException
		{
			final PasswordValidationCallback.PlainTextPasswordRequest plainTextPasswordRequest = (PasswordValidationCallback.PlainTextPasswordRequest) request;
			final String username = plainTextPasswordRequest.getUsername();
			final String password = plainTextPasswordRequest.getPassword();

			return authenticate(username, password);
		}
	}

	/**
	 * Authenticate user using authentication manager from spring.
	 *
	 * @param username the username
	 * @param password the password
	 * @return true, if successful
	 */
	private boolean authenticate (String username, String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken = new CUsernamePasswordUnitAuthentication(username, password, null);

		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication.isAuthenticated();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet () throws Exception
	{
	}
}
