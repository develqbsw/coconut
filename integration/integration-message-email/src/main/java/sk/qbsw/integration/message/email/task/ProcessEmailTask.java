package sk.qbsw.integration.message.email.task;

import org.springframework.scheduling.annotation.Scheduled;

import sk.qbsw.integration.message.service.ProcessMessageService;
import sk.qbsw.integration.message.task.ProcessMessageTask;

/**
 * The process email task.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class ProcessEmailTask extends ProcessMessageTask
{
	/**
	 * Instantiates a new Email task.
	 *
	 * @param messageService the message service
	 */
	public ProcessEmailTask (ProcessMessageService messageService)
	{
		super(messageService);
	}

	@Scheduled (cron = "${coconut.integration.message.email.process-task.cron}")
	@Override
	public void run ()
	{
		process();
	}
}
