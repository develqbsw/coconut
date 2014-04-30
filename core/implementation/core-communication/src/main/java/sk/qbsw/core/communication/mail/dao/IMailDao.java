package sk.qbsw.core.communication.mail.dao;

import java.io.Serializable;

import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.persistence.dao.IEntityDao;

/**
 * The interface for mail dao.
 *
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
public interface IMailDao extends Serializable, IEntityDao<Long, CMail>
{
}
