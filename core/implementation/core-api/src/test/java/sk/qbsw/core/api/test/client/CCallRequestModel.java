package sk.qbsw.core.api.test.client;

/**
 * Test request
 * 
 * 
 * @author Dalibor Rak
 *
 */
public class CCallRequestModel
{
	private String value = "ľščťžýáíéúô";

	public String getValue ()
	{
		return value;
	}

	public void setValue (String value)
	{
		this.value = value;
	}
}
