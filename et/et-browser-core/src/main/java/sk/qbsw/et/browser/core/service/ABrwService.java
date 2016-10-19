package sk.qbsw.et.browser.core.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.core.dto.CBrwDto;
import sk.qbsw.et.browser.core.model.CJoinDescriptor;

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
	 * @see sk.qbsw.et.browser.core.service.IBrwService#read(java.io.Serializable)
	 */
	@Override
	public T read (PK id)
	{
		return dao.findOne(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IBrwService#getAllByFilter(com.querydsl.core.types.Predicate, org.springframework.data.domain.Pageable, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public CBrwDto<PK, T> findAll (Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors)
	{
		return convertPageToBrwDto(dao.findAll(predicate, pageable, joinDescriptors));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.service.IBrwService#count(com.querydsl.core.types.Predicate)
	 */
	@Override
	public long count (Predicate predicate)
	{
		return dao.count(predicate);
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
