package sk.qbsw.sgwt.winnetou.client.ui.event.component;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when screen title is changed
 * 
 * @author Dalibor Rak
 * 
 */
public class CScreenTitleChangeEvent extends AApplicationEvent
{
	private String title;
	private String id;

	public CScreenTitleChangeEvent(IApplicationEventSource source, String id, String title)
	{
		super(source);
		this.title = title;
		this.id = id;
	}

	public String getDescription()
	{
		return "Title was changed";
	}

	public String getTitle()
	{
		return title;
	}

	public String getId()
	{
		return id;
	}
}
