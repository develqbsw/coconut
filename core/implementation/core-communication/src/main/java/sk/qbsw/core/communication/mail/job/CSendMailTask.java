package sk.qbsw.core.communication.mail.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;


/**
 * The task to send email using job.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public class CSendMailTask
{
	/** The sending attempt counts limit. */
	private final int SENDING_ATTEMPT_COUNTS_LIMIT = 5;

	/** The jpa mail dao. */
	@Autowired
	@Qualifier ("jpaMailDao")
	private IMailDao jpaMailDao;

	/** The sender mail dao. */
	@Autowired
	@Qualifier ("senderMailDao")
	private IMailDao senderMailDao;

	/**
	 * Checks database and send mail.
	 */
	@Transactional
	public void run ()
	{
		List<CMail> unsentMails = jpaMailDao.findAll(EMailState.UNSENT);

		for (CMail unsentMail : unsentMails)
		{
			try
			{
				senderMailDao.save(unsentMail);
				unsentMail.setState(EMailState.SENT);
			}
			catch (CSystemException e)
			{
				unsentMail.setAttemptCounter(unsentMail.getAttemptCounter() + 1);
				if (unsentMail.getAttemptCounter() > SENDING_ATTEMPT_COUNTS_LIMIT)
				{
					unsentMail.setState(EMailState.ERROR);
				}
			}

			jpaMailDao.save(unsentMail);
		}
	}
}
