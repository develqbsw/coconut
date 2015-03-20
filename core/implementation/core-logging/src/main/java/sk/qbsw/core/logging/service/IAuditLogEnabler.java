package sk.qbsw.core.logging.service;

/**
 * The Interface IAuditLogEnabler.
 * 
 * @version 1.12.4
 * @since 1.12.4
 * @authore Dalibor Rak
 */
public interface IAuditLogEnabler {

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isAuditLogEnabled();

}
