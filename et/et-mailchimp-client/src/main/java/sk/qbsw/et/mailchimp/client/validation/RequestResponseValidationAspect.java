package sk.qbsw.et.mailchimp.client.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import sk.qbsw.et.mailchimp.client.communication.JacksonFactory;
import sk.qbsw.et.mailchimp.client.exception.MailchimpException;
import sk.qbsw.et.mailchimp.client.exception.MailchimpTooManyRequestsException;
import sk.qbsw.et.mailchimp.client.model.response.MailchimpErrorResponseBody;

/**
 * The aspect checks if the user request own data.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 *
 */
@Aspect
@Component
public class RequestResponseValidationAspect
{
	/** The local validator. */
	@Autowired
	private Validator localValidator;

	/**
	 * Validate request and response.
	 *
	 * @param joinPoint the join point
	 * @param request the request
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around ("execution(* sk.qbsw.lomnicagallery.mailchimp.client.service.MailchimpClientImpl.*(..)) && args(request, ..)")
	public Object validateRequestAndResponse (ProceedingJoinPoint joinPoint, Object request) throws Throwable
	{
		validateRequest(request);

		try
		{
			Object response = joinPoint.proceed();

			validateResponse(response);
			return response;
		}
		catch (RestClientResponseException ex)
		{
			MailchimpErrorResponseBody errorResponse = JacksonFactory.createJacksonObjectMapper().readValue(ex.getResponseBodyAsByteArray(), MailchimpErrorResponseBody.class);
			String exceptionMessage = errorResponse.getTitle() + ": " + errorResponse.getDetail();

			if (ex.getRawStatusCode() == 429)
			{
				throw new MailchimpTooManyRequestsException(exceptionMessage, ex);
			}
			else
			{
				throw new MailchimpException(exceptionMessage, ex);
			}
		}
	}

	/**
	 * Validate request.
	 *
	 * @param <T> the generic type
	 * @param request the request
	 * @throws MailchimpException the ICS exception
	 */
	private <T> void validateRequest (T request) throws MailchimpException
	{
		if (request != null)
		{
			Set<ConstraintViolation<T>> constraintViolations = localValidator.validate(request);
			if (!constraintViolations.isEmpty())
			{
				throw new MailchimpException("Request validation failed");
			}
		}
	}

	/**
	 * Validate response.
	 *
	 * @param <T> the generic type
	 * @param response the response
	 * @throws MailchimpException the ICS exception
	 */
	private <T> void validateResponse (T response) throws MailchimpException
	{
		if (response != null)
		{
			Set<ConstraintViolation<T>> constraintViolations = localValidator.validate(response);

			if (!constraintViolations.isEmpty())
			{
				throw new MailchimpException("Response validation failed");
			}
		}
	}
}
