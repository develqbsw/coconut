/**
 * 
 */
package sk.qbsw.core.pay.base.csob.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
@XmlRootElement (name = "messageToVOD")
public class CsobMessageToVOD
{
	private String channel;

	private String body;

	public String getChannel ()
	{
		return channel;
	}

	@XmlElement (name = "Channel")
	public void setChannel (String channel)
	{
		this.channel = channel;
	}

	public String getBody ()
	{
		return body;
	}

	@XmlElement (name = "Body")
	public void setBody (String body)
	{
		this.body = body;
	}

}
