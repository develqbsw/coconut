package sk.qbsw.et.rquery.rsql.api.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import sk.qbsw.et.rquery.rsql.binding.model.OffsetPageRequest;
import sk.qbsw.et.rquery.rsql.binding.model.OffsetPageable;

/**
 * Extracts paging information from web requests and thus allows injecting {@link OffsetPageable} instances into controller methods.
 *
 * @author Tomas Lauro
 * @version 2.4.0
 * @since 2.4.0
 * @see PageableHandlerMethodArgumentResolver
 */
public class OffsetPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver
{
	private static final String DEFAULT_OFFSET_PARAMETER = "offset";
	public static final OffsetPageable DEFAULT_OFFSET_PAGE_REQUEST = OffsetPageRequest.of(0, 20);

	private String offsetParameterName = DEFAULT_OFFSET_PARAMETER;
	private Pageable fallbackPageable = DEFAULT_OFFSET_PAGE_REQUEST;

	/**
	 * Instantiates a new Offset pageable handler method argument resolver.
	 */
	public OffsetPageableHandlerMethodArgumentResolver ()
	{
		super();
		super.setPageParameterName(offsetParameterName);
		super.setFallbackPageable(fallbackPageable);
	}

	/**
	 * Instantiates a new Offset pageable handler method argument resolver.
	 *
	 * @param sortResolver the sort resolver
	 */
	public OffsetPageableHandlerMethodArgumentResolver (SortHandlerMethodArgumentResolver sortResolver)
	{
		super(sortResolver);
		super.setPageParameterName(offsetParameterName);
		super.setFallbackPageable(fallbackPageable);
	}

	/**
	 * Instantiates a new Offset pageable handler method argument resolver.
	 *
	 * @param sortResolver the sort resolver
	 */
	public OffsetPageableHandlerMethodArgumentResolver (@Nullable SortArgumentResolver sortResolver)
	{
		super(sortResolver);
		super.setPageParameterName(offsetParameterName);
		super.setFallbackPageable(fallbackPageable);
	}

	@Override
	public boolean supportsParameter (MethodParameter parameter)
	{
		return OffsetPageable.class.equals(parameter.getParameterType());
	}

	@Override
	public Pageable resolveArgument (MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory)
	{
		assertOffsetPageableUniqueness(methodParameter);
		Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

		return pageable.isPaged() ? OffsetPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()) : pageable;
	}

	/**
	 * Assert offset pageable uniqueness.
	 *
	 * @param parameter the parameter
	 */
	protected void assertOffsetPageableUniqueness (MethodParameter parameter)
	{
		Method method = parameter.getMethod();

		if (method == null)
		{
			throw new IllegalArgumentException(String.format("Method parameter %s is not backed by a method.", parameter));
		}

		if (containsMoreThanOnePageableParameter(method))
		{
			Annotation[][] annotations = method.getParameterAnnotations();
			assertQualifiersFor(method.getParameterTypes(), annotations);
		}
	}

	/**
	 * Returns whether the given {@link Method} has more than one {@link Pageable} parameter.
	 *
	 * @param method must not be {@literal null}.
	 * @return
	 */
	private boolean containsMoreThanOnePageableParameter (Method method)
	{
		boolean pageableFound = false;

		for (Class<?> type : method.getParameterTypes())
		{

			if (pageableFound && type.equals(OffsetPageable.class))
			{
				return true;
			}

			if (type.equals(OffsetPageable.class))
			{
				pageableFound = true;
			}
		}

		return false;
	}

	/**
	 * Asserts that every {@link Pageable} parameter of the given parameters carries an {@link Qualifier} annotation to distinguish them from each other.
	 *
	 * @param parameterTypes must not be {@literal null}.
	 * @param annotations must not be {@literal null}.
	 */
	protected static void assertQualifiersFor (Class<?>[] parameterTypes, Annotation[][] annotations)
	{
		Set<String> values = new HashSet<>();

		for (int i = 0; i < annotations.length; i++)
		{
			if (OffsetPageable.class.equals(parameterTypes[i]))
			{
				Qualifier qualifier = findAnnotation(annotations[i]);

				if (null == qualifier)
				{
					throw new IllegalStateException("Ambiguous OffsetPageable arguments in handler method. If you use multiple parameters of type OffsetPageable you need to qualify them with @Qualifier");
				}

				if (values.contains(qualifier.value()))
				{
					throw new IllegalStateException("Values of the user Qualifiers must be unique!");
				}

				values.add(qualifier.value());
			}
		}
	}

	/**
	 * Returns a {@link Qualifier} annotation from the given array of {@link Annotation}s. Returns {@literal null} if the array does not contain a {@link Qualifier} annotation.
	 *
	 * @param annotations must not be {@literal null}.
	 * @return
	 */
	@Nullable
	private static Qualifier findAnnotation (Annotation[] annotations)
	{
		for (Annotation annotation : annotations)
		{
			if (annotation instanceof Qualifier)
			{
				return (Qualifier) annotation;
			}
		}

		return null;
	}
}
