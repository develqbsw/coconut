package sk.qbsw.core.logging.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.security.core.parameters.DefaultSecurityParameterNameDiscoverer;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.logging.aspect.param.AParameter;
import sk.qbsw.core.logging.aspect.param.AParameterFactory;
import sk.qbsw.core.logging.aspect.param.CLoggedParameterFactory;
import sk.qbsw.core.logging.aspect.param.CNotLoggedParameterFactory;


/**
 * Abstract class for logging to share common methods between logging aspects
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 *
 */
public abstract class ALoggingAspect
{

	static final Logger LOGGER = LoggerFactory.getLogger("auditLog");

	static final Map<Class<?>, Map<Method, CMethodRepresentation>> MAPPING_CACHE = new HashMap<>();

	public ALoggingAspect ()
	{
		super();
	}

	/**
	 * get log which represent method - method name, parameters(parameter is not logged when is annotated with {@link CNotAuditLogged})
	 * contains cache to call one method with same parameters multiply times
	 * @param targetClass class on which is method called
	 * @param signature method signature which represent method 
	 * @param logResult tells us if are parameters logged
	 * @return method representation for logging
	 */
	protected CMethodRepresentation getMethodRepresentation (final Class<? extends Object> targetClass, final MethodSignature signature, final boolean logResult)
	{
		CMethodRepresentation result;
		final Method originalMethod = signature.getMethod();
		Map<Method, CMethodRepresentation> classSpecificMethodMapping;

		synchronized (MAPPING_CACHE)
		{
			classSpecificMethodMapping = MAPPING_CACHE.get(targetClass);

			if (classSpecificMethodMapping == null)
			{
				classSpecificMethodMapping = new HashMap<>();
				MAPPING_CACHE.put(targetClass, classSpecificMethodMapping);
			}
		}

		synchronized (classSpecificMethodMapping)
		{
			result = classSpecificMethodMapping.get(originalMethod);

			if (result == null)
			{
				Method method = originalMethod;

				//if is method interface then try get information about overriden method
				if (method.getDeclaringClass().isInterface())
				{
					try
					{
						method = targetClass.getMethod(signature.getName(), signature.getParameterTypes());
					}
					catch (NoSuchMethodException | SecurityException e)
					{
						LOGGER.error("Failed to get real implementation of interface method: {}. JoinPoint target class: {}. Using original method.", method.toGenericString(), targetClass);
						LOGGER.error("Failed to get real implementation of interface method", e);
					}
				}

				//log parameters
				final List<AParameterFactory> parameterFactories = logParameters(method);

				final String signatureString = method.toGenericString();
				result = new CMethodRepresentation(signatureString, logResult, parameterFactories);

				classSpecificMethodMapping.put(originalMethod, result);
			}
		}

		return result;
	}

	/**
	 * create logging parameters factory for parameters
	 * @param method - method for which are parameters logged
	 * @return parameter factories for parameters
	 */
	private List<AParameterFactory> logParameters (Method method)
	{
		final String signatureString = method.toGenericString();
		final List<AParameterFactory> parameterFactories = new ArrayList<>();
		final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultSecurityParameterNameDiscoverer();
		final String[] paramNames = parameterNameDiscoverer.getParameterNames(method);

		if (paramNames == null)
		{
			LOGGER.warn("Unable to resolve method parameter names for method {}. Debug symbol information is required if you are using parameter names in expressions.", signatureString);
		}

		final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		int parameterIndex = 0;

		if (parametersAnnotations.length > 0)
		{
			for (final Annotation[] parameterAnnotations : parametersAnnotations)
			{
				final String parameterName = (paramNames != null) ? paramNames[parameterIndex] : "arg" + parameterIndex;
				final AParameterFactory argumentFactory = createLoggedParameterFactory(parameterAnnotations, parameterName);

				parameterFactories.add(argumentFactory);

				parameterIndex++;
			}
		}
		return parameterFactories;
	}

	/**
	 * create factory which is ready to log parameter
	 * this method create loggingFactory
	 * @param parameterAnnotations annotations with which is parameter annotated
	 * @param parameterName string mane of parameter
	 * @return factory which logging parameter
	 */
	private AParameterFactory createLoggedParameterFactory (final Annotation[] parameterAnnotations, final String parameterName)
	{
		final AParameterFactory argumentFactory;

		boolean logParameter = checkParameterLogging(parameterAnnotations);

		if (logParameter)
		{
			argumentFactory = new CLoggedParameterFactory(parameterName);
		}
		else
		{
			argumentFactory = new CNotLoggedParameterFactory(parameterName);
		}
		return argumentFactory;
	}

	protected List<AParameter> getArguments (ProceedingJoinPoint pjp, final CMethodRepresentation methodRepresentation)
	{
		final List<AParameterFactory> parameterFactories = methodRepresentation.getParameterFactories();
		final List<AParameter> loggedArguments = new ArrayList<>(parameterFactories.size());
		final Object[] arguments = pjp.getArgs();

		int parameterIndex = 0;

		//prepare arguments to log
		for (final AParameterFactory pf : parameterFactories)
		{
			loggedArguments.add(pf.getInstance(arguments[parameterIndex]));
			parameterIndex++;
		}
		return loggedArguments;
	}

	/**
	 * check if have to logging parameter value
	 * if parameter is annotated with CNotAuditLogged then is their value not logged
	 * @param parameterAnnotations annotations annotations which have parameter
	 * @return true if parameter is logged false otherwise
	 */
	protected abstract boolean checkParameterLogging (final Annotation[] parameterAnnotations);

}
