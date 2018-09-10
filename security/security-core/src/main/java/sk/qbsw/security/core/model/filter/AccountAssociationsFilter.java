package sk.qbsw.security.core.model.filter;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.state.ActivityStates;

/**
 * The account associations filter.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.0
 */
@Getter
@Setter
public class AccountAssociationsFilter
{
	private String organizationCode;

	private ActivityStates state;

	private String groupCode;

	private String roleCode;

	private String excludedLogin;
}
