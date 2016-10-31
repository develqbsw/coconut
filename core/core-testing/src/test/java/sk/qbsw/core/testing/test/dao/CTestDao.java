package sk.qbsw.core.testing.test.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * The test dao.
 *
 * @author Tomas Lauro
 * @version 1.9.0
 * @since 1.9.0
 */
@Repository (value = "testDao")
public class CTestDao implements ITestDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.testing.test.dao.ITestDao#findAll()
	 */
	@Override
	public List<Integer> findAll ()
	{
		return Arrays.asList(2, 3, 5);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.testing.test.dao.ITestDao#find()
	 */
	@Override
	public int find ()
	{
		return 6;
	}
}
