package sk.qbsw.et.browser.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository;
import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The abstract filter service.
 *
 * @param <PK> the pk type
 * @param <T> the entity type
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public abstract class AFilterService<PK extends Serializable, T extends IEntity<PK>> implements IFilterService<PK, T>
{
	/** The dao. */
	@Autowired
	protected IFetchCapableQueryDslJpaRepository<T, PK> dao;

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IFilterService#findAll(boolean, com.querydsl.core.types.Predicate, org.springframework.data.domain.Sort)
	 */
	@Override
	public List<T> findAll (boolean distinct, Predicate predicate, Sort sorting)
	{
		return (List<T>) dao.findAll(distinct, predicate, sorting);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IFilterService#findAll(boolean, com.querydsl.core.types.Predicate, org.springframework.data.domain.Sort, sk.qbsw.core.persistence.model.CJoinDescriptor[])
	 */
	@Override
	public List<T> findAll (boolean distinct, Predicate predicate, Sort sorting, CJoinDescriptor<?>... joinDescriptors)
	{
		return dao.findAll(distinct, predicate, sorting, joinDescriptors);
	}
}
