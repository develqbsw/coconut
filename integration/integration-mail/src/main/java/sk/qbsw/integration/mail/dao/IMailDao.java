package sk.qbsw.integration.mail.dao;

import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.integration.mail.model.domain.CMail;
import sk.qbsw.integration.mail.model.domain.EMailState;

import java.util.List;

/**
 * The interface for mail dao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.9.0
 */
public interface IMailDao extends ICrudDao<Long, CMail>
{

	/**
	 * Finds and returns all entities with defined state.
	 *
	 * @param state the state
	 * @return list of entities
	 */
	List<CMail> findAllQueued(EMailState state);

	/**
	 * Find by subject.
	 *
	 * @param subject the subject
	 * @return the list of entities
	 */
	List<CMail> findBySubject(String subject);
}
