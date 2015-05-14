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

	private Long id;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.model.domain.IEntity#getId()
	 */
	@Override
	public Long getId ()
	{
		return id;
	}

	public CTestEntity ()
	{

	}

	public CTestEntity (Long id)
	{
		this.id = id;
	}

}
