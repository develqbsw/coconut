package sk.qbsw.core.testing.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.core.testing.test.dao.ITestDao;

/**
 * The test service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
@Service ("testService")
public class CTestService implements ITestService
{
	/** The test dao. */
	@Autowired
	private ITestDao testDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.testing.test.service.ITestService#doSomething()
	 */
	@Override
	public int doSomething ()
	{
		return testDao.find();
	}
}
