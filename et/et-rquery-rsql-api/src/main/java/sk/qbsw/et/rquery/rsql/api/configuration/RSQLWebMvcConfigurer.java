package sk.qbsw.et.rquery.rsql.api.configuration;

import java.util.List;

import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The RSQL web mvc configurer.
 *
 * @author Tomas Lauro
 * @version 2.4.0
 * @since 2.4.0
 */
@Import (RSQLApiConfiguration.class)
public class RSQLWebMvcConfigurer implements WebMvcConfigurer
{
	private final PageableArgumentResolver offsetPageableResolver;

	public RSQLWebMvcConfigurer (PageableArgumentResolver offsetPageableResolver)
	{
		this.offsetPageableResolver = offsetPageableResolver;
	}

	@Override
	public void addArgumentResolvers (List<HandlerMethodArgumentResolver> argumentResolvers)
	{
		argumentResolvers.add(offsetPageableResolver);
	}
}
