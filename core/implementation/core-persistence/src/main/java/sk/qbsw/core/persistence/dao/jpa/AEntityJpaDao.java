package sk.qbsw.core.persistence.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Class implements base methods for entity JPA DAO.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.13.0
 * @param <PK> the generic type for Entity Primary key
 * @param <T> the generic type for Entity itself
 * @since 1.0.0
 */
@SuppressWarnings("serial")
@CLogged
public abstract class AEntityJpaDao<PK, T extends IEntity<PK>> extends AJpaDao implements IEntityDao<PK, T>
{

	/** The entity class. */
	private Class<T> entityClass;

	/**
	 * Instantiates a new a entity jpa dao.
	 *
	 * @param entityClass the entity class
	 */
	public AEntityJpaDao(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		String str = "from " + entityClass.getName();
		Query query = em.createQuery(str);
		return (List<T>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findById(List<PK> ids)
	{
		String str = "select en from " + entityClass.getName() + " en where en.id IN :ids";
		Query query = em.createQuery(str);
		query.setParameter("ids", ids);
		return (List<T>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#read(java.lang.Object)
	 */
	@Override
	public T read(PK id)
	{
		return em.find(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove(T object)
	{
		em.remove(object);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#save(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public T update(T object)
	{
		if (object.getId() != null)
		{
			return em.merge(object);
		}
		else
		{
			em.persist(object);
			return object;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#invalidate(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void invalidate(T e) {
		// Not implemented.
		throw new IllegalStateException("Invalidate operation must be implemented in subclass.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#validate(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void validate(T e) {
		// Not implemented.
		throw new IllegalStateException("Validate operation must be implemented in subclass.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#create(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public PK create(T object) {
		this.em.persist(object);
		return object.getId();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.lang.Object)
	 */
	@Override
	public T findById(PK id)
	{
		return em.find(entityClass, id);
	}

}
