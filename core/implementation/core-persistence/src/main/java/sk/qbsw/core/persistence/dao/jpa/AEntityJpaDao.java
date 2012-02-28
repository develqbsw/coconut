package sk.qbsw.core.persistence.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Class contains same base methods for entity JPA DAO implementation
 * 
 * @author Dalibor Rak
 * @since 1.0.0
 * @version 1.0.0
 * 
 * @see sk.qbsw.airlines.dao.IEntityDao
 */
public abstract class AEntityJpaDao<T extends IEntity> implements IEntityDao<T>
{

	private Class<T> entityClass;

	@PersistenceContext (name = "persistenceContext")
	private EntityManager em;

	public EntityManager getEntityManager ()
	{
		return em;
	}

	public AEntityJpaDao (Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}

	/**
	 * Removes entity from persistent space
	 * 
	 * @param object
	 *            input entity
	 */
	public void remove (T object)
	{
		em.remove(object);
	}

	/**
	 * Save entity to persistent space
	 * 
	 * @param object
	 *            input entity
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
	 * Finds and returns all entities
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

	/**
	 * Finds and retirns entity by id
	 * 
	 * @return entity
	 */
	public T findById (Long id)
	{
		return em.find(entityClass, id);
	}

	/**
	 * Flushes call
	 */
	public void flush(){
		em.flush();
	}
}
