package sk.qbsw.security.organization.complex.core.service.mapper;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.UserInputData;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.service.mapper.UserInputDataMapper;
import sk.qbsw.security.core.service.mapper.UserInputDataMapperBase;
import sk.qbsw.security.organization.complex.base.model.CXOUserInputData;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;

import java.util.stream.Collectors;

/**
 * The complex organization user input data mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOUserInputDataMapperImpl extends UserInputDataMapperBase implements UserInputDataMapper
{
	@Override
	protected User instantiateUserWithCustomAttributes (UserInputData inputData)
	{
		CXOUserInputData cxoUserInputData = (CXOUserInputData) inputData;
		CXOUser user = new CXOUser();

		user.setState(ActivityStates.ACTIVE);
		user.setUnits( //
			cxoUserInputData.getUnits().stream().map(u -> {
				CXOUnit unit = new CXOUnit();
				unit.setCode(u.getCode());
				return unit;
			}).collect(Collectors.toSet()) //
		);

		return user;
	}
}
