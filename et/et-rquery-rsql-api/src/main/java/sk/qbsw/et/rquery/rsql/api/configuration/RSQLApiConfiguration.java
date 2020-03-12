package sk.qbsw.et.rquery.rsql.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import sk.qbsw.et.rquery.rsql.api.resolver.OffsetPageableHandlerMethodArgumentResolver;

/**
 * The RSQL api configuration.
 *
 * @author Tomas Lauro
 * @version 2.4.0
 * @since 2.4.0
 */
@EnableSpringDataWebSupport
public class RSQLApiConfiguration
{
	private final SortHandlerMethodArgumentResolver sortResolver;

	/**
	 * Instantiates a new Rsql offset pageable resolver configuration.
	 *
	 * @param sortResolver the sort resolver
	 */
	public RSQLApiConfiguration (SortHandlerMethodArgumentResolver sortResolver)
	{
		this.sortResolver = sortResolver;
	}

	@Bean
	public PageableArgumentResolver offsetPageableResolver ()
	{
		return new OffsetPageableHandlerMethodArgumentResolver(sortResolver);
	}
}
