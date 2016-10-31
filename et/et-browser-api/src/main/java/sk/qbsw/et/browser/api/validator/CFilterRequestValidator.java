package sk.qbsw.et.browser.api.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sk.qbsw.et.browser.client.model.filter.CFilterCriterionTransferObject;
import sk.qbsw.et.browser.client.model.request.CFilterRequest;

/**
 * The filter request validator.
 * 
 * @author Tomas Lauro
 *
 * @version 1.16.0
 * @since 1.16.0
 */
@Component ("filterRequestValidator")
public class CFilterRequestValidator implements Validator
{
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports (Class<?> clazz)
	{
		return CFilterRequest.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate (Object target, Errors errors)
	{
		CFilterRequest<?> request = (CFilterRequest<?>) target;

		if (request.getFilterCriteria() != null)
		{
			for (CFilterCriterionTransferObject<?> criterion : request.getFilterCriteria().getCriteria())
			{
				if (criterion.getNumberValue() == null && criterion.getStringValue() == null)
				{
					errors.reject("The filter criterion must contain number or string value");
				}

				if (criterion.getNumberValue() != null && criterion.getStringValue() != null)
				{
					errors.reject("The filter criterion must contain only number or only string value");
				}
			}
		}
	}
}
