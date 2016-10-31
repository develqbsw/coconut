package sk.qbsw.core.configuration.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.configuration.dao.ISystemParameterDao;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;

/**
 * Service CSystemParameterService
 *
 * @author Michal Lacko
 * 
 * @version 1.8.0
 * @since 1.8.0
 */
@Service
public class CSystemParameterService extends AService implements ISystemParameterService
{
	@Autowired
	private ISystemParameterDao systemParameterDao;

	@Override
	@Transactional(readOnly = true)
	public CSystemParameter findByName(String name)
	{
		return systemParameterDao.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CSystemParameter> findByModule(String module)
	{
		return systemParameterDao.findByModule(module);
	}

	@Override
	@Transactional(readOnly = true)
	public CSystemParameter findByName(String name, OffsetDateTime validDateTime)
	{
		return systemParameterDao.findByName(name, validDateTime);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CSystemParameter> findByModule(String module, OffsetDateTime validDateTime)
	{
		return systemParameterDao.findByModule(module, validDateTime);
	}

	@Override
	@Transactional
	public void save(CSystemParameter... systemParameters)
	{
		for (CSystemParameter systemParameter : systemParameters)
		{
			systemParameterDao.update(systemParameter);
		}
	}
}
