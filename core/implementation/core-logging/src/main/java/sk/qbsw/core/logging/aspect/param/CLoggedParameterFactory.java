package sk.qbsw.core.logging.aspect.param;

/**
 * factory to create {@link CLoggedParameter} parameter with their name
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
public class CLoggedParameterFactory extends AParameterFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CLoggedParameterFactory (String parameterName) {
		super(parameterName);
	}

	@Override
	public CLoggedParameter getInstance (Object parameterValue) {
		return new CLoggedParameter(this.parameterName, parameterValue);
	}

}
