package sk.qbsw.core.browser.core.service;

import java.util.List;

import com.mysema.query.types.expr.SimpleExpression;

import sk.qbsw.core.browser.core.model.CFilterParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The filter service.
 * 
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @since 1.15.0
 * @version 1.15.0
 */
public interface IFilterService<PK, T extends IEntity<PK>>
{
	List<T> getColumnValuesList (final SimpleExpression<T> column, final List<? extends CFilterParameter> filter, boolean ascendingOrder);
}
