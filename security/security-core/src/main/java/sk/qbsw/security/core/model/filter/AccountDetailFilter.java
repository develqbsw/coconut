package sk.qbsw.security.core.model.filter;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.state.ActivityStates;

/**
 * Account details filter.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
@Getter
@Setter
public class AccountDetailFilter
{
	private String login;

	private String email;

	private ActivityStates state;

	private String groupCodePrefix;

	private String organizationCode;
}
