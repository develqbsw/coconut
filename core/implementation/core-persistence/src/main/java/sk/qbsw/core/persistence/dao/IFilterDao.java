package sk.qbsw.core.persistence.dao;

import java.util.List;

import com.mysema.query.types.expr.SimpleExpression;

import sk.qbsw.core.persistence.model.CFilterParameter;

/**
 * The Interface IFilterDAO.
 */
public interface IFilterDao {

	/**
	 * Gets the colummn values list.
	 *
	 * @param <X> the generic type
	 * @param column the column
	 * @param fixedFilter the fixed filter
	 * @return the colummn values list
	 */
	public <X>List<X> getColummnValuesList (SimpleExpression<X> column, List<? extends CFilterParameter> fixedFilter);
}
