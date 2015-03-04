package sk.qbsw.core.security.web.filter;

/**
 * Filter for allowing call from other (all) domains. Setts following header attributes to response:
 * - Access-Control-Allow-Origin : *
 * - Access-Control-Allow-Methods : GET, POST, PUT, DELETE
 * - Access-Control-Allow-Headers : Content-Type
 * - Access-Control-Max-Age : 1800
 *
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.12.0
 * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS
 */
public class CCorsAPIXSSAllowFilter extends ACustomCorsAPIXSSAllowFilter {

	/** The Constant ALL. */
	private static final String ALL = "*";

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.web.filter.ACustomCorsAPIXSSAllowFilter#getAllowedOrigin()
	 */
	@Override
	protected String getAllowedOrigin() {
		return ALL;
	}
}
