package sk.qbsw.core.api.test.client;

/**
 * Test request
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CCallRequestModel
{
	private String value = "asdfg";

	public String getValue ()
	{
		return value;
	}

	public void setValue (String value)
	{
		this.value = value;
	}
}
