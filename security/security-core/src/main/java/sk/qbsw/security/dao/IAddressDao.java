/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.dao;

import java.io.Serializable;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.model.domain.CAddress;

/**
 * The Interface IAddressDao.
 *
 * @author Michal Lacko
 * @version 1.7.1
 * @since 1.6.0
 */
public interface IAddressDao extends Serializable, IEntityDao<Long, CAddress>
{
}
