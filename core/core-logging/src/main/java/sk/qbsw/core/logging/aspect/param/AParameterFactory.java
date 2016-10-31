package sk.qbsw.core.logging.aspect.param;

import java.io.Serializable;

/**
 * abstract parameter factory which include common parameter name 
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
public abstract class AParameterFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected final String parameterName;

	protected AParameterFactory (String parameterName) {
		this.parameterName = parameterName;
	}

	public abstract AParameter getInstance (Object parameterValue);

}
