package sk.qbsw.core.security.model.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

/**
 * The Class CAuthenticationConfigurator.
 * 
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.7.2
 */
@Service ("authenticationConfigurator")
@ManagedResource (objectName = "sk.qbsw.core.security:name=authenticationConfigurator", description = "authentication configuration")
public class CAuthenticationConfigurator implements IAuthenticationConfigurator
{
	/** The password pattern. */
	private String passwordPattern;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator#getPasswordPattern()
	 */
	@Override
	public String getPasswordPattern ()
	{
		return passwordPattern;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator#setPasswordPattern(java.lang.String)
	 */
	@Override
	public void setPasswordPattern (String passwordPattern)
	{
		this.passwordPattern = passwordPattern;
	}
}
