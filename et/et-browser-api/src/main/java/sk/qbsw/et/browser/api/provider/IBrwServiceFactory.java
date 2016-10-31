package sk.qbsw.et.browser.api.provider;

import java.io.Serializable;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.core.service.IFilterService;
import sk.qbsw.et.browser.core.service.IBrwService;

/**
 * The browser service factory.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwServiceFactory
{
	/**
	 * Gets the brw service.
	 *
	 * @param <PK> the generic type
	 * @param <T> the generic type
	 * @param browserCode the browser code
	 * @return the brw service
	 */
	<PK extends Serializable, T extends IEntity<PK>> IBrwService<PK, T> getBrwService (String browserCode);

	/**
	 * Gets the filter service.
	 *
	 * @param <PK> the generic type
	 * @param <T> the generic type
	 * @param browserCode the browser code
	 * @return the filter service
	 */
	<PK extends Serializable, T extends IEntity<PK>> IFilterService<PK, T> getBrwFilterService (String browserCode);
}
