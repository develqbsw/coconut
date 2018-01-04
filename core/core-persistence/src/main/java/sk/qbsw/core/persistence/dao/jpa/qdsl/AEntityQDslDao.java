package sk.qbsw.core.persistence.dao.jpa.qdsl;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * The entity query dsl dao.
 *
 * @param <K> the primary key - must extend Number and Comparable
 * @param <T> the entity
 * @version 1.14.2
 * @since 1.15.2
 */
public abstract class AEntityQDslDao<K extends Number & Comparable<?>, T extends IEntity<K>> extends AQDslDao<K, T> implements IEntityDao<K, T> {
    /**
     * The entity path crud.
     */
    protected EntityPathBase<T> entityPathCRUD = null;

    /**
     * The primary key class.
     */
    protected Class<K> primaryKeyClass = null;

    /**
     * Instantiates a new a common query dsl dao.
     *
     * @param entityPathCRUD  the entity path crud
     * @param primaryKeyClass the primary key class
     */
    public AEntityQDslDao(EntityPathBase<T> entityPathCRUD, Class<K> primaryKeyClass) {
        this.entityPathCRUD = entityPathCRUD;
        this.primaryKeyClass = primaryKeyClass;
    }

    /**
     * Default constructor is not supported
     */
    private AEntityQDslDao() {
        // nothing to do
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.ICrudDao#create(sk.qbsw.core.persistence.model.domain.IEntity)
     */
    @Override
    public K create(T object) {
        this.em.persist(object);

        return object.getId();
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.ICrudDao#read(java.lang.Object)
     */
    @Override
    public T read(K id) {
        final Class<? extends T> cls = this.entityPathCRUD.getType();

        final T result = this.em.find(cls, id);

        if (result == null) {
            throw new EntityNotFoundException("Entity (CRUD) of class " + cls + " with primary key " + id + " was not found.");
        }

        return result;
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.ICrudDao#update(sk.qbsw.core.persistence.model.domain.IEntity)
     */
    @Override
    public T update(T object) {
        if (object.getId() != null) {
            return this.em.merge(object);
        } else {
            this.em.persist(object);
            return object;
        }
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.ICrudDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
     */
    @Override
    public void remove(T object) {
        em.remove(object);
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.IEntityDao#clear()
     */
    @Override
    public void clear() {
        em.clear();
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
     * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
     */
    @Override
    public List<T> findAll() {
        PathBuilder<T> qEntity = new PathBuilder<>(entityPathCRUD.getType(), entityPathCRUD.getMetadata());

        return queryFactory.selectFrom(qEntity).fetch();
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.lang.Object)
     */
    @Override
    public T findById(K id) {
        PathBuilder<T> qEntity = new PathBuilder<>(entityPathCRUD.getType(), entityPathCRUD.getMetadata());
        NumberPath<K> number = qEntity.getNumber("id", primaryKeyClass);

        JPAQuery<T> query = queryFactory.selectFrom(entityPathCRUD).where(number.eq(id));
        return CQDslDaoHelper.handleUniqueResultQuery(query);
    }

    /* (non-Javadoc)
     * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
     */
    @Override
    public List<T> findById(List<K> ids) {
        PathBuilder<T> qEntity = new PathBuilder<>(entityPathCRUD.getType(), entityPathCRUD.getMetadata());
        NumberPath<K> number = qEntity.getNumber("id", primaryKeyClass);

        return queryFactory.selectFrom(qEntity).where(number.in(ids)).fetch();
    }
}
