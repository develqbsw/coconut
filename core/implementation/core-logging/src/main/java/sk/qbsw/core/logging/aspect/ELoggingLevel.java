package sk.qbsw.core.logging.aspect;

import sk.qbsw.core.logging.aspect.annotation.CLogged;

/**
 * Logging level which is set to {@link CLogged} annotation.
 *
 * @author Michal Lacko
 * @author Marián Oravec
 * @version 1.8.0
 * @since 1.8.0
 */
public enum ELoggingLevel
{

	/** The trace. */
	TRACE, /** The debug. */
	DEBUG, /** The info. */
	INFO, /** The warn. */
	WARN, /** The error. */
	ERROR

}
