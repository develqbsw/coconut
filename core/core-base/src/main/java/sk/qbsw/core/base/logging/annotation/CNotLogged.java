package sk.qbsw.core.base.logging.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which mark method or parameter and avoid logging.
 * If is class marked with {@link CLogged} and method {@link CNotLogged} then method is not logged
 * If method is marked with {@link CLogged} and parameter with {@link CNotLogged} then parameter is not logged
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * 
 * @version 1.11.6
 * @since 1.8.0
 * 
 */
@Target (value = {ElementType.METHOD, ElementType.PARAMETER})
@Retention (value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CNotLogged {
}
