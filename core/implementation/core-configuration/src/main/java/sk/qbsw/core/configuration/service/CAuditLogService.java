package sk.qbsw.core.configuration.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import sk.qbsw.core.configuration.dao.ISystemParameterDao;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;

/**
 * Service xml serialziation
 *
 * @author Michal Lacko
 * 
 * @version 1.8.0
 * @since 1.8.0
 */
@Service
public class CAuditLogService implements ISystemParameterService
{
	private ISystemParameterDao systemParameterDao; 

	@Override
	public CSystemParameter findByName (String name)
	{
		return systemParameterDao.findByName(name);
	}

	@Override
	public List<CSystemParameter> findByModule (String module)
	{
		return systemParameterDao.findByModule(module);
	}

	@Override
	public CSystemParameter findByName (String name, DateTime validDateTime)
	{
		return systemParameterDao.findByName(name, validDateTime);
	}

	@Override
	public List<CSystemParameter> findByModule (String module, DateTime validDateTime)
	{
		return systemParameterDao.findByModule(module, validDateTime);
	}

}
