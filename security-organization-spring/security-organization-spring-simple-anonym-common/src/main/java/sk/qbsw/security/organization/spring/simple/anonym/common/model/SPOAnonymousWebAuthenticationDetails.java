package sk.qbsw.security.organization.spring.simple.anonym.common.model;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * This WebAuthenticationDetails implementation allows for storing a organization id.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.4
 */
public class SPOAnonymousWebAuthenticationDetails extends WebAuthenticationDetails
{
	private final String organizationId;

	/**
	 * Instantiates a new Anonymous web authentication details.
	 *
	 * @param request the request
	 * @param organizationId the organization id
	 */
	public SPOAnonymousWebAuthenticationDetails (HttpServletRequest request, String organizationId)
	{
		super(request);
		this.organizationId = organizationId;
	}

	/**
	 * Gets organization id.
	 *
	 * @return the organization id
	 */
	public String getOrganizationId ()
	{
		return organizationId;
	}

	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; ");
		sb.append(organizationId);
		return sb.toString();
	}

	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}
}
