package sk.qbsw.core.communication.mail.dao.jpa;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;

/**
 * The jpa dao for mail.
 *
 * @author Tomas Lauro
 * @version 1.9.0
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
}
