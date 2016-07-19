package sk.qbsw.et.browser.api.provider;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.request.IBrwRequest;
import sk.qbsw.et.browser.client.request.IFilterRequest;
import sk.qbsw.et.browser.core.dto.IBrwDto;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedVariableMappingException;

/**
 * The browser data provider.
 *
 * @param <F> the filterable
 * @param <PK> the pk
 * @param <T> the entity
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwDataProvider<F extends IFilterable, PK extends Serializable, T extends IEntity<PK>>
{
	/**
	 * Initialize mapping.
	 */
	void initializeMapping ();

	/**
	 * Gets the data.
	 *
	 * @param request the request
	 * @return the data
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	IBrwDto<PK, T> getData (IBrwRequest<F> request) throws CBrwUndefinedVariableMappingException;

	/**
	 * Gets the data.
	 *
	 * @param request the request
	 * @return the data
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	List<T> getData (IFilterRequest<F> request) throws CBrwUndefinedVariableMappingException;
}
