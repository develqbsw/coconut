package sk.qbsw.core.persistence.dao.hibernate;

import org.hibernate.proxy.HibernateProxy;

/**
 * Hibernate supporting methods.
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public class CHibernateUtils {

	/**
	 * Deproxy classes in inheritance
	 *
	 * @param <T> the generic type
	 * @param maybeProxy the maybe proxy
	 * @param baseClass the base class
	 * @return the T instance 
	 * @throws ClassCastException if there is a problem with the casting
	 */
	public static <T> T deproxy(Object maybeProxy, Class<T> baseClass) throws ClassCastException {
		if (maybeProxy instanceof HibernateProxy) {
			return baseClass.cast(((HibernateProxy) maybeProxy).getHibernateLazyInitializer().getImplementation());
		}
		return baseClass.cast(maybeProxy);
	}

}
