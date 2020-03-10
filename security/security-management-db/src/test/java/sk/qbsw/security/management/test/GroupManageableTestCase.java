package sk.qbsw.security.management.test;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.base.model.GroupDataTypes;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.management.model.GroupManageableOutputData;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.management.service.GroupManagementService;
import sk.qbsw.security.management.test.util.DataGenerator;

/**
 * The group manageable test case.
 *
 * @author Michal Slezák
 * @version 2.5.0
 * @since 2.5.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
public class GroupManageableTestCase
{
	@Autowired
	private DataGenerator dataGenerator;

	@Autowired
	private GroupManagementService groupManagementService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private AccountPermissionManagementService accountPermissionManagementService;

	/**
	 * Test initialization.
	 */
	@Test
	public void testInitialization ()
	{
		assertNotNull("Could not find group service", groupManagementService);
	}

	private void initTest ()
	{
		dataGenerator.generateDatabaseDataWithGroupManageableForDatabaseTests();
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAll ()
	{
		initTest();

		List<GroupManageableOutputData> groupManageables = groupManagementService.findAll();

		// asserts
		assertNotNull("Find all group manageables failed: list of groups is null", groupManageables);
		Assert.assertEquals("Find all group manageables failed: the size of list of groups is not 1", 1, groupManageables.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateGroupManageable () throws CBusinessException
	{
		initTest();

		User user = userDao.findAll().get(0);

		GroupManageableOutputData groupManageable = groupManagementService.create("GROUP_CODE_1", GroupDataTypes.STANDARD, "CATEGORY", null, null, user.getId());

		// asserts
		assertNotNull("Group manageable has not been created", groupManageable);
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testUpdateRolesInGroupManageable () throws CBusinessException
	{
		initTest();

		User user = userDao.findAll().get(0);

		List<Role> roles = roleDao.findAll();

		Set<Long> roleIds = new HashSet<>();
		roleIds.add(roles.get(0).getId());

		GroupManageableOutputData groupManageable = groupManagementService.create("GROUP_CODE_3", GroupDataTypes.STANDARD, "CATEGORY", roleIds, null, user.getId());

		Assert.assertEquals("Group code doesn't match!", "GROUP_CODE_3", groupManageable.getCode());
		Assert.assertEquals("Role code doesn't match!", "unit_test_role_manageable_group_1", groupManageable.getRoles().iterator().next().getCode());

		roleIds.clear();
		roleIds.add(roles.get(1).getId());

		groupManageable = groupManagementService.update(groupManageable.getId(), "GROUP_CODE_3_UPDATED", GroupDataTypes.STANDARD, null, roleIds, null, user.getId());

		// asserts
		Assert.assertEquals("Group code hasn't changed!", "GROUP_CODE_3_UPDATED", groupManageable.getCode());
		Assert.assertEquals("Role code hasn't changed!", "unit_test_role_manageable_group_2", groupManageable.getRoles().iterator().next().getCode());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testDeleteGroupManageable () throws CBusinessException
	{
		initTest();

		List<GroupManageableOutputData> groupManageables = groupManagementService.findAll();

		// asserts
		Assert.assertEquals("Count of Group manageables doesn't match!", 1, groupManageables.size());

		groupManagementService.deleteGroup(groupManageables.get(0).getId());

		groupManageables = groupManagementService.findAll();

		// asserts
		Assert.assertEquals("Count of Group manageables doesn't match!", 0, groupManageables.size());
	}



}
