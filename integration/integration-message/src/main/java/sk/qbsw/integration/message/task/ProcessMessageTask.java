package sk.qbsw.integration.message.task;

import sk.qbsw.integration.message.service.ProcessMessageService;

/**
 * The process message task.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class ProcessMessageTask
{
	private final ProcessMessageService messageService;

	/**
	 * Instantiates a new Process message task.
	 *
	 * @param messageService the message service
	 */
	public ProcessMessageTask (ProcessMessageService messageService)
	{
		this.messageService = messageService;
	}

	/**
	 * Process.
	 */
	protected void process ()
	{
		messageService.processMessage();
	}

	/**
	 * Run - call process method inside.
	 */
	public abstract void run ();
}
