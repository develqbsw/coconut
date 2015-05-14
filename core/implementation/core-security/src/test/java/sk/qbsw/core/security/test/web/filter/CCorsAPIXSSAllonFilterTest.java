package sk.qbsw.core.security.test.web.filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import sk.qbsw.core.security.web.filter.CCorsAPIXSSAllowFilter;

/**
 * Filter for checking write to the MDC.
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public class CCorsAPIXSSAllonFilterTest {

	/**
	 * Test do filter.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDoFilter() throws Exception {
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		when(httpServletRequest.getHeader("Access-Control-Request-Method")).thenReturn("Access-Control-Request-Method");
		when(httpServletRequest.getMethod()).thenReturn("OPTIONS");

		MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

		FilterChain filterChain = mock(FilterChain.class);

		CCorsAPIXSSAllowFilter filter = new CCorsAPIXSSAllowFilter();
		filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		Assert.assertTrue(httpServletResponse.containsHeader("Access-Control-Allow-Origin"));
	}

	/**
	 * Test do filter fail.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDoFilterFail() throws Exception {
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		when(httpServletRequest.getMethod()).thenReturn("OPTIONS");

		MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

		FilterChain filterChain = mock(FilterChain.class);

		CCorsAPIXSSAllowFilter filter = new CCorsAPIXSSAllowFilter();
		filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		Assert.assertFalse(httpServletResponse.containsHeader("Access-Control-Allow-Origin"));
	}
	
	/**
	 * Test do filter fail2.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDoFilterFail2() throws Exception {
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		when(httpServletRequest.getHeader("Access-Control-Request-Method")).thenReturn("Access-Control-Request-Method");

		MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();

		FilterChain filterChain = mock(FilterChain.class);

		CCorsAPIXSSAllowFilter filter = new CCorsAPIXSSAllowFilter();
		filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

		Assert.assertFalse(httpServletResponse.containsHeader("Access-Control-Allow-Origin"));
	}


}
