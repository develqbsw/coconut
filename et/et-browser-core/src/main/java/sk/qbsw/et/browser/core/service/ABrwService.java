package sk.qbsw.et.browser.core.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.core.dto.CBrwDto;

/**
 * The abstract brw service.
 *
 * @param <PK> the pk type
 * @param <T> the entity type
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public abstract class ABrwService<PK extends Serializable, T extends IEntity<PK>>extends AFilterService<PK, T> implements IBrwService<PK, T>
{
	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IBrwService#findOne(java.io.Serializable, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public T findOne (PK id, CJoinDescriptor<?>... joinDescriptors)
	{
		return dao.findOne(id, joinDescriptors);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IBrwService#findAll(boolean, com.querydsl.core.types.Predicate, org.springframework.data.domain.Pageable, sk.qbsw.core.persistence.model.CJoinDescriptor[])
	 */
	@Override
	public CBrwDto<PK, T> findAll (boolean distinct, Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors)
	{
		return convertPageToBrwDto(dao.findAll(distinct, predicate, pageable, joinDescriptors));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IBrwService#count(boolean, com.querydsl.core.types.Predicate, sk.qbsw.core.persistence.model.CJoinDescriptor[])
	 */
	@Override
	public long count (boolean distinct, Predicate predicate, CJoinDescriptor<?>... joinDescriptors)
	{
		return dao.count(distinct, predicate, joinDescriptors);
	}

	/**
	 * Convert page to brw dto.
	 *
	 * @param page the page
	 * @return the i brw dto
	 */
	private CBrwDto<PK, T> convertPageToBrwDto (Page<T> page)
	{
		return new CBrwDto<>(page.getContent(), page.getTotalElements());
	}
}
