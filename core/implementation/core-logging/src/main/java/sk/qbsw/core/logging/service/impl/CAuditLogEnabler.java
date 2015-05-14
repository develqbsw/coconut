package sk.qbsw.core.logging.service.impl;

import org.springframework.stereotype.Service;

import sk.qbsw.core.logging.service.IAuditLogEnabler;

/**
 * Audit log enabler
 * 
 * @version 1.12.4
 * @since 1.12.4
 * @author Dalibor Rak
 */
@Service
public class CAuditLogEnabler implements IAuditLogEnabler {

	/** The enabled. */
	private Boolean enabled;

	/**
	 * Instantiates a new c audit log enabler.
	 */
	public CAuditLogEnabler() {
		enabled = Boolean.TRUE;
	}

	/**
	 * Enable.
	 */
	public void enable() {
		this.enabled = Boolean.TRUE;
	}

	/**
	 * Disable.
	 */
	public void disable() {
		this.enabled = Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.logging.service.IAuditLogEnabler#isEnabled()
	 */
	@Override
	public boolean isAuditLogEnabled() {
		return enabled;
	}

}
