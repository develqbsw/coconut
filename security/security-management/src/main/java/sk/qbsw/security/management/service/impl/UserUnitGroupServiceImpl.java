package sk.qbsw.security.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.management.service.UserUnitGroupService;

import java.util.List;

/**
 * Service for UserUnitGroup entity operations
 *
 * @author farkas.roman
 * @version 1.7.0
 * @since 1.7.0
 */
@Service("xUserUnitGroupService")
public class UserUnitGroupServiceImpl extends AService implements UserUnitGroupService
{
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The XUserUnitGroup dao.
     */
    @Autowired
    private AccountUnitGroupDao xuugDao;

    @Override
    @Transactional(readOnly = true)
    public List<AccountUnitGroup> getAll(Account user, Unit unit, Group group)
    {
        return xuugDao.findByUserAndUnitAndGroup(user, unit, group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountUnitGroup> getAllByUser(Account user)
    {
        return getAll(user, null, null);
    }

    @Override
    @Transactional
    public void save(AccountUnitGroup xuug)
    {
        xuugDao.update(xuug);
    }

    @Override
    @Transactional
    public void saveAll(List<AccountUnitGroup> xuugList)
    {
        for (AccountUnitGroup xuug : xuugList)
        {
            xuugDao.update(xuug);
        }
    }

    @Override
    @Transactional
    public void remove(AccountUnitGroup xuug)
    {
        if (xuug == null || xuug.getId() == null)
        {
            throw new IllegalArgumentException("input object to remove cannot be null and must have id");
        }

        AccountUnitGroup toDelete = xuugDao.findById(xuug.getId());
        xuugDao.remove(toDelete);
    }
}
