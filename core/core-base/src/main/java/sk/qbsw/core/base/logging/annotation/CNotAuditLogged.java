package sk.qbsw.core.base.logging.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for audit log which mark method or parameter and avoid logging.
 * If is class marked with {@link CLogged} and method {@link CNotLogged} then method is not logged
 * If method is marked with {@link CLogged} and parameter with {@link CNotLogged} then parameter is not logged
 * 
 * If the blockTree  is set to true, the the audit log is disabled for logging down in the hierarchy
 * 
 * @author Michal Lacko
 * @author Marián Oravec
 * @author Peter Bozik
 * 
 * @version 1.12.4
 * @since 1.8.0
 */
@Target (value = {ElementType.TYPE,ElementType.METHOD, ElementType.PARAMETER,ElementType.FIELD})
@Retention (value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CNotAuditLogged {
	boolean blockTree () default false;
}
