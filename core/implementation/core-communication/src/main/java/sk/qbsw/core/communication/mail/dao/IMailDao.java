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
 * 
 * @version 1.9.1
 * @since 1.9.0
 */
public interface IMailDao extends Serializable, IEntityDao<Long, CMail>
{
	/**
	 * Finds and returns all entities with defined state.
	 *
	 * @param state the state
	 * @return list of entities
	 */
	List<CMail> findAllQueued (EMailState state);

	/**
	 * Find by subject.
	 *
	 * @param subject the subject
	 * @return the list of entities
	 */
	List<CMail> findBySubject (String subject);
}
