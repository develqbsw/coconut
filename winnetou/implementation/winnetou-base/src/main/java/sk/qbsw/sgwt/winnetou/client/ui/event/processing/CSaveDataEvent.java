package sk.qbsw.sgwt.winnetou.client.ui.event.processing;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when data is beeing saved to the system.(Start of saving)
 */

public class CSaveDataEvent extends AApplicationEvent
{
	private Long recordId;

	public CSaveDataEvent(IApplicationEventSource source, Long recordId)
	{
		super(source);
		this.recordId = recordId;
	}

	public String getDescription()
	{
		return "Save of record";
	}

	public Long getRecordId()
	{
		return recordId;
	}
}
