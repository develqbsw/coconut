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
 * @param <PK> the generic type for Entity Primary key
 * @param <T> the generic type for Entity itself
 * @see sk.qbsw.airlines.dao.IEntityDao
 * 
 * @author Dalibor Rak
 * @since 1.0.0
 * @version 1.3.0
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

	/**
	 * Finds and returns all entities.
	 *
	 * @return list of entities
	 */
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
	@SuppressWarnings ("unchecked")
	public List<T> findById (List<PK> ids)
	{
		String str = "select en from " + entityClass.getName() + " en where en.id IN :ids";
		Query query = em.createQuery(str);
		query.setParameter("ids", ids);
		return (List<T>) query.getResultList();
	}

	/**
	 * Finds and retirns entity by id.
	 *
	 * @param id the id
	 * @return entity
	 */
	public T findById (PK id)
	{
		return em.find(entityClass, id);
	}

	/**
	 * Flushes call.
	 */
	public void flush ()
	{
		em.flush();
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
	 * Removes entity from persistent space.
	 *
	 * @param object input entity
	 */
	public void remove (T object)
	{
		em.remove(object);
	}

	/**
	 * Save entity to persistent space.
	 *
	 * @param object input entity
	 */
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
	 * Sets the entity manager.
	 *
	 * @param em the new entity manager
	 */
	public void setEntityManager (EntityManager em)
	{
		this.em = em;
	}
}
