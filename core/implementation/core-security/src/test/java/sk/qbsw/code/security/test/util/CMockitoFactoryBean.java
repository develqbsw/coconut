package sk.qbsw.code.security.test.util;

import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory returns the mock of class passed as parameter.
 *
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 * 
 * @param <T> class to be mocked
 */
//source: http://www.jayway.com/2011/11/30/spring-integration-tests-part-i-creating-mock-objects/
public class CMockitoFactoryBean<T> implements FactoryBean<T>
{
	/** The class to be mocked. */
	private Class<T> classToBeMocked;

	/**
	 * Creates a Mockito mock instance of the provided class.
	 * @param classToBeMocked The class to be mocked.
	 */
	public CMockitoFactoryBean (Class<T> classToBeMocked)
	{
		this.classToBeMocked = classToBeMocked;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public T getObject () throws Exception
	{
		return Mockito.mock(classToBeMocked);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType ()
	{
		return classToBeMocked;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton ()
	{
		return true;
	}
}
