/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.logging.dao.impl;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.logging.dao.IAuditLogDao;
import sk.qbsw.core.logging.model.domain.CAuditLog;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;

/**
 * The Class CAuditLog
 *
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */
@Repository (value = "auditLogDao")
public class CAuditLogJpaDao extends AEntityJpaDao<Long, CAuditLog> implements IAuditLogDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public CAuditLogJpaDao ()
	{
		super(CAuditLog.class);
	}
}
