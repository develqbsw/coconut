package sk.qbsw.security.authentication.ldap.test.performance.task;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.model.domain.CUser;

/**
 * The abstract class represents login task for test. The children of this class defines an authentication service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.7.2
 * @since 1.7.2
 */
public abstract class CLoginTask implements Runnable
{
	/** The name of task. */
	private String name;

	/** The login count. */
	private int loginCount;

	/**
	 * Inits.
	 *
	 * @param name the name
	 * @param loginCount the login count
	 */
	public synchronized void init (String name, int loginCount)
	{
		this.name = name;
		this.loginCount = loginCount;
		initResources();
	}

	/**
	 * Authenticate user. This method returns CUser if authentication success or throws an exception.
	 *
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	protected abstract CUser authenticate () throws CSecurityException;

	/**
	 * Inits the resources.
	 */
	protected abstract void initResources ();

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run ()
	{
		try
		{
			for (int i = 0; i < loginCount; i++)
			{
				if (authenticate() != null)
				{
					System.out.println(name + " iteration " + (i + 1) + " was successful");
				}
				else
				{
					System.out.println(name + " iteration failed: returned user was null");
				}
			}
		}
		catch (CSecurityException ex)
		{
			System.out.println(name + " iteration failed: " + ex.toString());
			throw new CSystemException("The login performance test failed", ex);
		}
	}
}
