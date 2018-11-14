package sk.qbsw.security.organization.spring.simple.base.model;

import sk.qbsw.security.spring.base.model.AccountDetails;

/**
 * The security simple organization account details.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.4
 */
public interface SimpleOrganizationAccountDetails extends AccountDetails
{
	/**
	 * Gets organization.
	 *
	 * @return the organization
	 */
	SimpleOrganization getOrganization ();
}
