package sk.qbsw.core.security.web.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import sk.qbsw.core.security.web.filter.CMDCFilter;

/**
 * Filter for checking write to the MDC
 * 
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public class CMDCFilterTest {

	/**
	 * Test do filter.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDoFilter() throws Exception {
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		Authentication auth = mock(Authentication.class);
		when(auth.getName()).thenReturn("Dalibor");

		SecurityContextHolder.getContext().setAuthentication(auth);

		FilterChain filterChain = mock(FilterChain.class);

		CMDCFilter filter = new CMDCFilter();
		filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		// test shouldn't fail
	}

}
