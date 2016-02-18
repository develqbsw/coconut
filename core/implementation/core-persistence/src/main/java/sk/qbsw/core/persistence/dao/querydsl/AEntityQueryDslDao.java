package sk.qbsw.core.persistence.dao.querydsl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import sk.qbsw.core.persistence.dao.ICrudListDao;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.CFilterParameter;
import sk.qbsw.core.persistence.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;

/**
 * The Class ACommonQueryDslDAO.
 *
 * @param <PK> the primary key - must extend Number and Comparable
 * @param <T> the entity
 * 
 * @version 1.14.2
 */
@SuppressWarnings ("serial")
public abstract class AEntityQueryDslDao<PK extends Number & Comparable<?>, T extends IEntity<PK>> extends AQueryDslDao<PK, T> implements IEntityDao<PK, T>, ICrudListDao<PK, T>
{
	/** The entity path crud. */
	protected final EntityPathBase<T> entityPathCRUD;

	/** The primary key class. */
	protected final Class<PK> primaryKeyClass;

	/**
	 * Instantiates a new a common query dsl dao.
	 *
	 * @param entityPathCRUD the entity path crud
	 * @param primaryKeyClass the primary key class
	 */
	public AEntityQueryDslDao (EntityPathBase<T> entityPathCRUD, Class<PK> primaryKeyClass)
	{
		this.entityPathCRUD = entityPathCRUD;
		this.primaryKeyClass = primaryKeyClass;
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
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#create(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public PK create (T object)
	{
		this.em.persist(object);

		return object.getId();
	}

	/**
	 * Creates the crud query.
	 *
	 * @return the JPA query
	 */
	private JPAQuery createCRUDQuery ()
	{
		if (this.entityPathCRUD == null)
		{
			throw new IllegalStateException("Cannot create query: pathBaseBRW is null. Subclass of " + this.getClass() + " passed null to super contructor call?");
		}

		return this.createQuery(this.entityPathCRUD);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.ICrudListDao#getCRUDCount(java.util.List)
	 */
	@Override
	public long getCRUDCount (final List<? extends CFilterParameter> wheres)
	{
		final JPAQuery q = this.createCRUDQuery();

		this.appendWherePart(q, wheres);

		final long result = this.getCount(q);

		return result;
	}

	/**
	 * Gets the CRUD list.
	 *
	 * @param q the q
	 * @return the CRUD list
	 */
	protected List<T> getCRUDList (final JPAQuery q)
	{
		return q.list(this.entityPathCRUD);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.ICrudListDao#getCRUDList(java.util.List, java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<T> getCRUDList (final List<? extends CFilterParameter> wheres, final List<COrderParameter> orderSpecifiers, final Long from, final Long count)
	{
		final JPAQuery q = this.createCRUDQuery();

		this.appendWherePart(q, wheres);
		this.appendOrderByPart(q, orderSpecifiers);
		this.appendLimit(q, from, count);

		final List<T> result = this.getCRUDList(q);

		return result;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#invalidate(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void invalidate (T e)
	{
		// Not implemented.
		throw new IllegalStateException("Invalidate operation must be implemented in subclass.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#read(java.lang.Object)
	 */
	@Override
	public T read (PK id)
	{
		final Class<? extends T> cls = this.entityPathCRUD.getType();

		final T result = this.em.find(cls, id);

		if (result == null)
		{
			throw new EntityNotFoundException("Entity (CRUD) of class " + cls + " with primary key " + id + " was not found.");
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (T object)
	{
		// Not implemented.
		throw new IllegalStateException("Delete operation must be implemented in subclass.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#update(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public T update (T object)
	{
		if (object.getId() != null)
		{
			return this.em.merge(object);
		}
		else
		{
			this.em.persist(object);
			return object;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.ICrudDao#validate(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void validate (T e)
	{
		// Not implemented.
		throw new IllegalStateException("Validate operation must be implemented in subclass.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
	 */
	@Override
	public List<T> findAll ()
	{
		PathBuilder<T> qEntity = new PathBuilder<T>(entityPathCRUD.getType(), entityPathCRUD.getMetadata());

		return new JPAQuery(getEntityManager()).from(qEntity).list(qEntity);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.lang.Object)
	 */
	@Override
	public T findById (PK id)
	{
		PathBuilder<T> qEntity = new PathBuilder<T>(entityPathCRUD.getType(), entityPathCRUD.getMetadata());
		NumberPath<PK> number = qEntity.getNumber("id", primaryKeyClass);

		JPAQuery query = new JPAQuery(getEntityManager()).from(entityPathCRUD).where(number.eq(id));
		return CQueryDslDaoHelper.handleUniqueResultQuery(query, entityPathCRUD);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
	 */
	@Override
	public List<T> findById (List<PK> ids)
	{
		PathBuilder<T> qEntity = new PathBuilder<T>(entityPathCRUD.getType(), entityPathCRUD.getMetadata());
		NumberPath<PK> number = qEntity.getNumber("id", primaryKeyClass);

		return new JPAQuery(getEntityManager()).from(qEntity).where(number.in(ids)).list(qEntity);
	}
}
