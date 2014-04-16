package sk.qbsw.core.security.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.core.security.exception.CPasswordFormatException;
import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;

/**
 * Authentication data validation service.
 *
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.7.2
 */
@Service (value = "authDataValidationService")
class CAuthDataValidationService implements IAuthDataValidationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthDataValidationService#validatePassword(java.lang.String)
	 */
	public void validatePassword (String password) throws CPasswordFormatException
	{
		if (authenticationConfigurator.getPasswordPattern() != null)
		{
			Matcher matcher = Pattern.compile(authenticationConfigurator.getPasswordPattern()).matcher(password);

			if (matcher.matches() == false)
			{
				throw new CPasswordFormatException("Incorrect password format");
			}
		}
	}
}
