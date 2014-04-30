package sk.qbsw.core.communication.mail.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * The job sends mail from database in periodic intervals. 
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public class CSendMailJob extends QuartzJobBean
{
	/** The send mail task. */
	private CSendMailTask sendMailTask;

	/**
	 * Sets the send mail task.
	 *
	 * @param sendMailTask the new send mail task
	 */
	public void setSendMailTask (CSendMailTask sendMailTask)
	{
		this.sendMailTask = sendMailTask;
	}

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal (JobExecutionContext context) throws JobExecutionException
	{
		sendMailTask.run();
	}
}
