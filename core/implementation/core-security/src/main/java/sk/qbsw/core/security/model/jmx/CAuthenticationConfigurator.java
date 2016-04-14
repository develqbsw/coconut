package sk.qbsw.core.security.model.jmx;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.model.domain.EAuthParameters;
import sk.qbsw.core.security.model.domain.EAuthSchema;
import sk.qbsw.core.security.model.domain.EHashMethod;

/**
 * The Class CAuthenticationConfigurator.
 * 
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.7.2
 */
@Service ("authenticationConfigurator")
@ManagedResource (objectName = "sk.qbsw.core.security:name=authenticationConfigurator", description = "authentication configuration")
public class CAuthenticationConfigurator extends AService implements IAuthenticationConfigurator
{
	/** The password pattern. */
	private String passwordPattern;

	/** The hash method to hash user password in LDAP. */
	private EHashMethod ldapPasswordHashMethod = EHashMethod.SSHA512;

	/** The hash method to hash user password in database. */
	private EHashMethod databasePasswordHashMethod = EHashMethod.SHA;

	/** The hash method to hash user password in database. */
	private EAuthSchema databaseAuthSchema  = EAuthSchema.CUSTOM;

	/** additiona parameters to configurator specific for  */
	private Map<EAuthParameters, String> additionalConfiguratorParameters = new HashMap<EAuthParameters, String>();

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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator#getLdapPasswordHashMethod()
	 */
	@Override
	public EHashMethod getLdapPasswordHashMethod ()
	{
		return ldapPasswordHashMethod;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator#setLdapPasswordHashMethod(sk.qbsw.core.security.model.domain.EHashMethod)
	 */
	@Override
	public void setLdapPasswordHashMethod (EHashMethod ldapPasswordHashMethod)
	{
		this.ldapPasswordHashMethod = ldapPasswordHashMethod;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator#getDatabasePasswordHashMethod()
	 */
	@Override
	public EHashMethod getDatabasePasswordHashMethod ()
	{
		return databasePasswordHashMethod;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator#setDatabasePasswordHashMethod(sk.qbsw.core.security.model.domain.EHashMethod)
	 */
	@Override
	public void setDatabasePasswordHashMethod (EHashMethod databasePasswordHashMethod)
	{
		this.databasePasswordHashMethod = databasePasswordHashMethod;
	}

	@Override
	public EAuthSchema getDatabaseAuthSchema ()
	{
		
		return databaseAuthSchema;
	}

	public void setDatabaseAuthSchema (EAuthSchema databaseAuthSchema)
	{
		this.databaseAuthSchema = databaseAuthSchema;
	}

	@Override
	public Map<EAuthParameters, String> getAdditionalAuthParameters ()
	{
		return additionalConfiguratorParameters;
	}
}
