package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class COAuthTokenRequest.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class COAuthTokenRequest
{
	private String scope;
	private String grant_type;

	public COAuthTokenRequest ()
	{
		//basic constructor
	}

	public COAuthTokenRequest (String scope)
	{
		this.scope = scope;
		this.grant_type = "client_credentials";

	}

	public String getGrant_type ()
	{
		return grant_type;
	}

	public void setGrant_type (String grant_type)
	{
		this.grant_type = grant_type;
	}

	public String getScope ()
	{
		return scope;
	}

	public void setScope (String scope)
	{
		this.scope = scope;
	}

}
