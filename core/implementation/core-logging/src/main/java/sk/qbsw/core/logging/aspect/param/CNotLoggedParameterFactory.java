package sk.qbsw.core.logging.aspect.param;

/**
 * factory to create {@link CNotLoggedParameter} parameter with their name
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
public class CNotLoggedParameterFactory extends AParameterFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CNotLoggedParameterFactory (String parameterName) {
		super(parameterName);
	}

	@Override
	public CNotLoggedParameter getInstance (Object parameterValue) {
		/*
		 * parameterValue is ignored in not logged parameter.
		 */
		return new CNotLoggedParameter(this.parameterName);
	}

}
