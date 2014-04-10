package sk.qbsw.core.logging.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sk.qbsw.core.logging.aspect.ELoggingLevel;

/**
 * annotation for logging. 
 * If is class annotated with {@link CLogged} then all methods and all their parameters in class are logged
 * If is method annotated with {@link CLogged} then all their parameters are logged
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @since 1.8.0
 * @version 1.8.0
 */
@Target (value = {ElementType.TYPE, ElementType.METHOD})
@Retention (value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CLogged {

	ELoggingLevel level () default ELoggingLevel.DEBUG;

	boolean logResult () default true;

}
