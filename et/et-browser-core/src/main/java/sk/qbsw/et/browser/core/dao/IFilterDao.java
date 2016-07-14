package sk.qbsw.et.browser.core.dao;

import java.util.List;

import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.core.persistence.dao.IDao;
import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.core.model.CFilterParameter;

/**
 * The filter dao interface.
 *
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @param <PK> the generic type
 * @param <T> the generic type
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public interface IFilterDao<PK, T extends IEntity<PK>>extends IDao
{
	/**
	 * Gets the colummn values list.
	 *
	 * @param column the column
	 * @param filter the filter
	 * @param ascendingOrder the ascending order
	 * @return the colummn values list
	 */
	List<T> getColumnValuesList (final SimpleExpression<T> column, final List<? extends CFilterParameter> filter, boolean ascendingOrder);
}
