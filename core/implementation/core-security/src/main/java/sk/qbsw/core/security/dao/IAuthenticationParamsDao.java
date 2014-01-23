/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;

/**
 * The Interface IAuthenticationParamsDao.
 *
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
public interface IAuthenticationParamsDao extends Serializable, IEntityDao<Long, CAuthenticationParams>
{
}
