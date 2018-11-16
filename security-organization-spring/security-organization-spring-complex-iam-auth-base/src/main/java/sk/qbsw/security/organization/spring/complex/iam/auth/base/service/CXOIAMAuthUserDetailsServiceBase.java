package sk.qbsw.security.organization.spring.complex.iam.auth.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.organization.complex.base.model.CXOOrganizationOutputData;
import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganization;
import sk.qbsw.security.organization.spring.complex.base.model.ComplexOrganizationUnit;
import sk.qbsw.security.organization.spring.complex.iam.auth.base.model.CXOIAMAuthLoggedUser;
import sk.qbsw.security.spring.base.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;
import sk.qbsw.security.spring.iam.auth.common.service.IAMAuthUserDetailsServiceCommon;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public abstract class CXOIAMAuthUserDetailsServiceBase<T> extends IAMAuthUserDetailsServiceCommon<T>
{
	/**
	 * The Logger.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new Iam auth user details service base.
	 *
	 * @param authorityConverter the authority converter
	 */
	public CXOIAMAuthUserDetailsServiceBase (AuthorityConverter authorityConverter) {
		super(authorityConverter);
	}

	@Override
	protected UserDetails createUserDetails (AccountData accountData, IAMAuthData iamAuthData)
	{
		List<CXOOrganizationOutputData> organizationsOutputData = ((CXOUserOutputData) accountData.getUser()).getOrganizations();

		List<ComplexOrganization> organizations = organizationsOutputData.stream().map(o -> new ComplexOrganization(o.getId(), o.getName(), o.getCode(), o.getUnits().stream().map(u -> new ComplexOrganizationUnit(u.getId(), u.getName(), u.getCode())).collect(Collectors.toList()))).collect(Collectors.toList());

		return new CXOIAMAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", authorityConverter.convertRolesToAuthorities(accountData.getRoles()), accountData.getUser().getId(), organizations, iamAuthData);
	}
}
