package sk.qbsw.core.browser.dao;

import java.util.List;

import sk.qbsw.core.browser.dto.CBRWDataDTO;
import sk.qbsw.core.persistence.dao.querydsl.IFilterDao;
import sk.qbsw.core.persistence.model.CFilterParameter;
import sk.qbsw.core.persistence.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The Interface IBRWDao.
 *
 * @param <PK> primary key
 * @param <T> browser entity 
 */
public interface IBRWDao<PK, T extends IEntity<PK>> extends IFilterDao {

	/**
	 * Gets the complete brw list.
	 *
	 * @param wheres the wheres
	 * @param orderSpecifiers the order specifiers
	 * @param from the from
	 * @param count the count
	 * @return the complete brw list
	 */
	public CBRWDataDTO<PK, T> getCompleteBRWList(final List<? extends CFilterParameter> wheres, List<COrderParameter> orderSpecifiers, Long from, Long count);

	/**
	 * Gets the BRW list.
	 *
	 * @param wheres the wheres
	 * @param orderSpecifiers the order specifiers
	 * @param from the from
	 * @param count the count
	 * @return the BRW list
	 */
	public List<T> getBRWList(final List<? extends CFilterParameter> wheres, List<COrderParameter> orderSpecifiers, Long from, Long count);

	/**
	 * Gets the BRW count.
	 *
	 * @param wheres the wheres
	 * @return the BRW count
	 */
	public long getBRWCount(final List<? extends CFilterParameter> wheres);

	/**
	 * Read from view.
	 *
	 * @param id the id
	 * @return the t
	 */
	public T readFromView(PK id);

}
