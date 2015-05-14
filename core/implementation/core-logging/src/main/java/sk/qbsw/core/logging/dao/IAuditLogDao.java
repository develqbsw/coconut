/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.logging.dao;

import java.io.Serializable;

import sk.qbsw.core.logging.model.domain.CAuditLog;
import sk.qbsw.core.persistence.dao.IEntityDao;

/**
 * The Interface IAuditLogDao.
 *
 * @author Michal Lacko
 * @version 1.7.1
 * @since 1.6.0
 */
public interface IAuditLogDao extends Serializable, IEntityDao<Long, CAuditLog>
{
}
