package sk.qbsw.security.organization.spring.complex.base.model;

import sk.qbsw.security.spring.base.model.AccountDetails;

import java.util.List;

/**
 * The security complex organization account details.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ComplexOrganizationAccountDetails extends AccountDetails
{
	/**
	 * Gets organizations.
	 *
	 * @return the organizations
	 */
	List<ComplexOrganization> getOrganizations ();
}
