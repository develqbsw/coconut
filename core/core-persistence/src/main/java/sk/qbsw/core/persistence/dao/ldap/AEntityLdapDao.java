package sk.qbsw.core.persistence.dao.ldap;

import java.util.List;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Class implements base methods for entity LDAP DAO.
 *
 * @param <PK> the generic type for Entity Primary key
 * @param <T> the generic type for Entity itself
 * @see sk.qbsw.core.persistence.dao.IEntityDao
 * 
 * @author Tomas Lauro
 * 
 * @since 1.6.0
 * @version 1.13.0
 */
public abstract class AEntityLdapDao<PK, T extends IEntity<PK>> implements IEntityDao<PK, T>, ICrudDao<PK, T>
{
	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#save(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public T update (T object)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (T object)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
	 */
	@Override
	public List<T> findAll ()
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
	 */
	@Override
	public List<T> findById (List<PK> ids)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#flush()
	 */
	@Override
	public void flush ()
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#clear()
	 */
	@Override
	public void clear ()
	{
		throw new CSystemException("The not implemented method.");
	}
}
