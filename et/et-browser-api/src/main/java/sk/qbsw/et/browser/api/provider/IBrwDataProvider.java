package sk.qbsw.et.browser.api.provider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.api.mapping.CBrwEntityMapping;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.request.CBrwRequest;
import sk.qbsw.et.browser.client.model.request.CFilterRequest;
import sk.qbsw.et.browser.core.dto.CBrwDto;
import sk.qbsw.et.browser.core.exception.CBrwBusinessException;

/**
 * The browser data provider.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwDataProvider
{
	/**
	 * Gets the one.
	 *
	 * @param <PK> the generic type
	 * @param <T> the generic type
	 * @param browserCode the browser code
	 * @param id the id
	 * @return the one
	 * @throws CBrwBusinessException the c brw business exception
	 */
	<PK extends Serializable, T extends IEntity<PK>> T getOne (String browserCode, PK id) throws CBrwBusinessException;

	/**
	 * Gets the data.
	 *
	 * @param <F> the filterable
	 * @param <PK> the pk
	 * @param <T> the entity
	 * @param browserCode the browser code
	 * @param request the request
	 * @return the data
	 * @throws CBrwBusinessException the c brw business exception
	 */
	<F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> CBrwDto<PK, T> getData (String browserCode, CBrwRequest<F> request) throws CBrwBusinessException;

	/**
	 * Count.
	 *
	 * @param <F> the generic type
	 * @param browserCode the browser code
	 * @param request the request
	 * @return the long
	 * @throws CBrwBusinessException the c brw business exception
	 */
	<F extends IFilterable> long count (String browserCode, CBrwRequest<F> request) throws CBrwBusinessException;

	/**
	 * Gets the data.
	 *
	 * @param <F> the filterable
	 * @param <PK> the pk
	 * @param <T> the entity
	 * @param browserCode the browser code
	 * @param request the request
	 * @return the data
	 * @throws CBrwBusinessException the c brw business exception
	 */
	<F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> List<T> getData (String browserCode, CFilterRequest<F> request) throws CBrwBusinessException;

	/**
	 * Sets the mapping.
	 *
	 * @param mapping the mapping
	 */
	void setMapping (Map<String, CBrwEntityMapping<?>> mapping);

	/**
	 * Sets the data converter.
	 *
	 * @param dataConverter the new data converter
	 */
	void setDataConverter (IBrwDataConverter dataConverter);

	/**
	 * Sets the service factory.
	 *
	 * @param serviceFactory the new service factory
	 */
	void setServiceFactory (IBrwServiceFactory serviceFactory);
}
