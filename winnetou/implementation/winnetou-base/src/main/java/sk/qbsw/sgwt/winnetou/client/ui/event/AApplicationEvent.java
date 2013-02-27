package sk.qbsw.sgwt.winnetou.client.ui.event;

/**
 * Base class for application event
 * 
 * @author Dalibor Rak
 * @version 0.1
 */
public abstract class AApplicationEvent implements IApplicationEvent
{
	private final IApplicationEventSource source;

	protected AApplicationEvent(IApplicationEventSource source)
	{
		this.source = source;
	}

	public IApplicationEventSource getSource()
	{
		return source;
	}
}
