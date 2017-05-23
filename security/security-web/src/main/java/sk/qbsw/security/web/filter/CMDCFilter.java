package sk.qbsw.security.web.filter;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.core.base.threadlocal.CThreadLocalStorage;
import sk.qbsw.core.base.threadlocal.IThreadLocalStorage;

/**
 * Filter for storing thread local data from spring security context.
 * 
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public class CMDCFilter implements Filter {

	/** The Constant MIN_RANDOM. */
	private static final long MIN_RANDOM = 1000000000L;

	/** The Constant MAX_RANDOM. */
	private static final long MAX_RANDOM = 10000000000L;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		CMDCFilter.fillMDC(SecurityContextHolder.getContext().getAuthentication());
		
		try {
			chain.doFilter(req, resp);
		} finally {
			CThreadLocalStorage.clear();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * Fill mdc.
	 *
	 * @param authentication the authentication
	 */
	public static void fillMDC(Authentication authentication) {
		final String name = (authentication != null) ? authentication.getName() : null;

		if (CThreadLocalStorage.getUniqueRequestIdValue() == null) {
			CThreadLocalStorage.put(IThreadLocalStorage.KEY_UNIQUE_REQUEST_ID, String.valueOf(ThreadLocalRandom.current().nextLong(MIN_RANDOM, MAX_RANDOM)));
		}

		if (name != null) {
			CThreadLocalStorage.put(IThreadLocalStorage.KEY_USER, name);
		}
	}
}
