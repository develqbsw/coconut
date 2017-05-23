package sk.qbsw.security.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter for allowing call from other (all) domains. Setts following header attributes to response:
 * - Access-Control-Allow-Origin : according resutl of getOrigin method
 * - Access-Control-Allow-Methods : GET, POST, PUT, DELETE
 * - Access-Control-Allow-Headers : Content-Type
 * - Access-Control-Max-Age : 1800
 * 
 * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS
 * 
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 */
public abstract class ACustomCorsAPIXSSAllowFilter extends OncePerRequestFilter {

	/**
	 * Method should be overriden to get appropriate origin URL. Method should cache value red from appropriate source, because may be called very often.
	 * Example response "http://foo.example"
	 *
	 * @return the allowed origin for request
	 */
	protected abstract String getAllowedOrigin();

	/* (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {

			// CORS "pre-flight" request
			response.addHeader("Access-Control-Allow-Origin", getAllowedOrigin());
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			response.addHeader("Access-Control-Max-Age", "1800");//30 min
		}
		filterChain.doFilter(request, response);
	}

}
