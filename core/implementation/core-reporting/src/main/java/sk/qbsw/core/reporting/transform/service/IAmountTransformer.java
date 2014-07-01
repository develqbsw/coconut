package sk.qbsw.core.reporting.transform.service;

import java.math.BigDecimal;

import sk.qbsw.core.reporting.transform.exception.CNotSupportedAmountException;
import sk.qbsw.core.reporting.transform.model.CTransformedAmount;

/**
* The transformer transforms the amount to {@link CTransformedAmount} object.
*
* @author Tomas Lauro
* 
* @version 1.10.2
* @since 1.10.2
*/
public interface IAmountTransformer
{
	/**
	 * Transform the amount to the format:
	 * - integer part: String,
	 * - fractional part: Long.
	 *
	 * @param amount the amount
	 * @return the transformed amount
	 * @throws CNotSupportedAmountException the not supported amount
	 */
	public CTransformedAmount<String, Long> transformToStringLongFormat (BigDecimal amount) throws CNotSupportedAmountException;
}
