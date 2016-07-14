package sk.qbsw.et.browser.client.model.filter;

import sk.qbsw.et.browser.client.model.IFilterable;

/**
 * The filter criteria validator.
 *
 * @author Tomas Lauro
 * 
 * @since 1.16.0
 * @version 1.16.0
 */
public interface IFilterCriteriaValidator<F extends IFilterable>
{
	/**
	 * Validate criterion.
	 *
	 * @param filterCriterion the filter criterion
	 * @return true, if successful
	 */
	boolean validateCriterion (CFilterCriterionTransferObject<F> filterCriterion);
}
