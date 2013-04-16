package sk.qbsw.core.persistence.test.dao.mock;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Testing entity.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public class CTestEntity implements IEntity<Long>
{

	private Long pkId;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return pkId;
	}

	public CTestEntity ()
	{

	}

	public CTestEntity (Long pkId)
	{
		this.pkId = pkId;
	}

}
