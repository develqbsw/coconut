package sk.qbsw.core.communication.mail.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;

/**
 * The jpa dao for mail.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.1
 * @since 1.9.0
 */
@Repository (value = "jpaMailDao")
public class CMailDao extends AEntityJpaDao<Long, CMail> implements IMailDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new mail jpa dao.
	 */
	public CMailDao ()
	{
		super(CMail.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.dao.IMailDao#findAllQueued(sk.qbsw.core.communication.mail.model.domain.EMailState)
	 */
	@SuppressWarnings ("unchecked")
	@Override
	public List<CMail> findAllQueued (EMailState state)
	{
		String strQuery = "select ma from CQueuedMail ma left join fetch ma.attachments where ma.state=:state";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("state", state);

		return (List<CMail>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.communication.mail.dao.IMailDao#findBySubject(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	@Override
	public List<CMail> findBySubject (String subject)
	{
		String strQuery = "select ma from CMail ma left join fetch ma.attachments where ma.subject=:subject";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("subject", subject);

		return (List<CMail>) query.getResultList();
	}
}
