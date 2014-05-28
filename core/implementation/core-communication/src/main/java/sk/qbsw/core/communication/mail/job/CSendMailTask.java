package sk.qbsw.core.communication.mail.job;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.communication.mail.dao.IMailDao;
import sk.qbsw.core.communication.mail.exception.CCommunicationException;
import sk.qbsw.core.communication.mail.model.domain.CMail;
import sk.qbsw.core.communication.mail.model.domain.EMailState;


/**
 * The task to send email using job.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.1
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
		List<CMail> unsentMails = jpaMailDao.findAllQueued(EMailState.UNSENT);

		for (CMail unsentMail : unsentMails)
		{
			try
			{
				senderMailDao.save(unsentMail);
				unsentMail.setState(EMailState.SENT);
				unsentMail.setSent(DateTime.now());
			}
			//exception in sending process - try again
			catch (CCommunicationException e)
			{
				unsentMail.setAttemptCounter(unsentMail.getAttemptCounter() + 1);
				if (unsentMail.getAttemptCounter() >= SENDING_ATTEMPT_COUNTS_LIMIT)
				{
					unsentMail.setState(EMailState.COMMUNICATION_ERROR);
				}
			}
			//exception in mail creating process - leave it
			catch (CSystemException e)
			{
				unsentMail.setState(EMailState.DATA_ERROR);
			}

			jpaMailDao.save(unsentMail);
		}
	}
}
