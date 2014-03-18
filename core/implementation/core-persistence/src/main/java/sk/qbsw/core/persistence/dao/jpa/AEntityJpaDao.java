package sk.qbsw.core.persistence.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Class implements base methods for entity JPA DAO.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @param <PK> the generic type for Entity Primary key
 * @param <T> the generic type for Entity itself
 * 
 * @since 1.0.0
 * @version 1.7.2
 */
public abstract class AEntityJpaDao<PK, T extends IEntity<PK>> implements IEntityDao<PK, T>
{
	/** The em. */
	@PersistenceContext (name = "persistenceContext")
	private EntityManager em;

	/** The entity class. */
	private Class<T> entityClass;

	/**
	 * Instantiates a new a entity jpa dao.
	 *
	 * @param entityClass the entity class
	 */
	public AEntityJpaDao (Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public List<T> findAll ()
	{
		String str = "from " + entityClass.getName();
		Query query = em.createQuery(str);
		return (List<T>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public List<T> findById (List<PK> ids)
	{
		String str = "select en from " + entityClass.getName() + " en where en.id IN :ids";
		Query query = em.createQuery(str);
		query.setParameter("ids", ids);
		return (List<T>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.lang.Object)
	 */
	@Override
	public T findById (PK id)
	{
		return em.find(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#flush()
	 */
	@Override
	public void flush ()
	{
		em.flush();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#clear()
	 */
	@Override
	public void clear ()
	{
		em.clear();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (T object)
	{
		em.remove(object);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#save(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void save (T object)
	{
		if (object.getId() != null)
		{
			em.merge(object);
		}
		else
		{
			em.persist(object);
		}
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager ()
	{
		return em;
	}

	/**
	 * Sets the entity manager.
	 *
	 * @param em the new entity manager
	 */
	public void setEntityManager (EntityManager em)
	{
		this.em = em;
	}
}
