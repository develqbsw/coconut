package sk.qbsw.core.communication.mail.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;
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
	/**
	 * Finds and returns all entities with defined state under defined attempt counts limit.
	 *
	 * @param state the state
	 * @return list of entities
	 */
	List<CMail> findAll (EMailState state);
}
