package sk.qbsw.core.logging.aspect;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.logging.aspect.param.AParameterFactory;

/**
 * Representation of data to log for one method.
 *
 * @author Michal Lacko
 * @author Marián Oravec
 * @version 1.8.0
 * @since 1.8.0
 */
public class CMethodRepresentation implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** method signature, which defines method(name, package, exceptions). */
	private final String methodSignature;
	
	/** attribute which tells us when is logged this log to logger. */
	private final boolean logResult;
	
	/** description of method parameters. */
	private final List<AParameterFactory> parameterFactories;

	/**
	 * Instantiates a new c method representation.
	 *
	 * @param methodSignature the method signature
	 * @param logResult the log result
	 * @param parameterFactories the parameter factories
	 */
	public CMethodRepresentation (String methodSignature, boolean logResult, List<AParameterFactory> parameterFactories) {
		this.methodSignature = methodSignature;
		this.logResult = logResult;
		this.parameterFactories = parameterFactories;
	}

	/**
	 * Gets the method signature.
	 *
	 * @return the method signature
	 */
	public String getMethodSignature () {
		return this.methodSignature;
	}

	/**
	 * Checks if is log result.
	 *
	 * @return true, if is log result
	 */
	public boolean isLogResult () {
		return this.logResult;
	}

	/**
	 * Gets the parameter factories.
	 *
	 * @return the parameter factories
	 */
	public List<AParameterFactory> getParameterFactories () {
		return this.parameterFactories;
	}

}
