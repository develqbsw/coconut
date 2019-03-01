package sk.qbsw.security.organization.spring.complex.base.mapper;

import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.spring.complex.common.model.CXOOrganization;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUnit;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUserData;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.base.mapper.UserDataMapperBase;
import sk.qbsw.security.spring.common.model.UserData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The complex organization user data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class CXOUserDataMapperImpl extends UserDataMapperBase<CXOUserOutputData> implements UserDataMapper<CXOUserOutputData>
{
	@Override
	protected UserData instantiateUserDataWithCustomAttributes (CXOUserOutputData user)
	{
		List<CXOOrganization> organizations = user.getOrganizations() //
			.stream() //
			.map(o -> new CXOOrganization(o.getId(), o.getName(), o.getCode(), o.getUnits() //
				.stream() //
				.map(u -> new CXOUnit(u.getId(), u.getName(), u.getCode())) //
				.collect(Collectors.toList()))) //
			.collect(Collectors.toList());

		return new CXOUserData( //
			user.getId(), //
			organizations //
		);
	}
}
