package sk.qbsw.core.browser.core.dao;

import java.util.List;

import sk.qbsw.core.browser.core.dto.CBrwDataDto;
import sk.qbsw.core.browser.core.model.CFilterParameter;
import sk.qbsw.core.browser.core.model.COrderParameter;
import sk.qbsw.core.persistence.dao.IDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The brw dao interface.
 *
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 * 
 * @param <PK> the primary key
 * @param <T> the entity type
 */
public interface IBrwDao<PK, T extends IEntity<PK>>extends IDao
{
	/**
	 * Read.
	 *
	 * @param entityPath the entity path
	 * @param id the id
	 * @return the t
	 */
	T read (final PK id);

	/**
	 * Gets the complete list.
	 *
	 * @param entityPath the entity path
	 * @param wheres the wheres
	 * @param orderSpecifiers the order specifiers
	 * @param offset the offset
	 * @param limit the limit
	 * @return the complete list
	 */
	CBrwDataDto<PK, T> getCompleteList (final List<? extends CFilterParameter> wheres, List<COrderParameter> orderSpecifiers, Long offset, Long limit);

	/**
	 * Gets the list.
	 *
	 * @param <T> the generic type
	 * @param entityPath the entity path
	 * @param wheres the wheres
	 * @param orderSpecifiers the order specifiers
	 * @param offset the offset
	 * @param count the count
	 * @return the BRW list
	 */
	List<T> getList (final List<? extends CFilterParameter> wheres, List<COrderParameter> orderSpecifiers, Long offset, Long count);

	/**
	 * Gets the count.
	 *
	 * @param <T> the generic type
	 * @param entityPath the entity path
	 * @param wheres the wheres
	 * @return the BRW count
	 */
	long getCount (final List<? extends CFilterParameter> wheres);
}
