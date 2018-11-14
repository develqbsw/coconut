package sk.qbsw.security.organization.spring.complex.anonym.base.model;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * This WebAuthenticationDetails implementation allows for storing a organization id.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class AnonymousWebAuthenticationDetails extends WebAuthenticationDetails
{
	private static final long serialVersionUID = -6650975453173214754L;

	private final String organizationId;

	/**
	 * Instantiates a new Anonymous web authentication details.
	 *
	 * @param request the request
	 * @param organizationId the organization id
	 */
	public AnonymousWebAuthenticationDetails (HttpServletRequest request, String organizationId)
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
