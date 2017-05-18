package sk.qbsw.core.security.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Class responsible for reading client's IP address from Header or from request.
 * 
 * @author Dalibor Rak
 * @version 1.12.2
 * @since 1.12.2
 */
public class CHttpClientAddressRetriever {

	/** The Constant HEADER_IP_ATTRIBUTE. */
	public static final String HEADER_IP_ATTRIBUTE = "X-Forwarded-For";

	/**
	 * Gets the client's remote ip address.
	 *
	 * @param httpRequest the http request
	 * @return the remote ip address
	 */
	public synchronized String getClientIpAddress(HttpServletRequest httpRequest) {
		String remoteAddress = httpRequest.getHeader(HEADER_IP_ATTRIBUTE);

		// if header address is empty, read one from the http request
		if (remoteAddress != null)
		{
			remoteAddress = remoteAddress.trim();
			if (remoteAddress.isEmpty()) {
				remoteAddress = httpRequest.getRemoteAddr();
			}
		}
		// if header address is empty, read one from HTTP request
		else {
			remoteAddress = httpRequest.getRemoteAddr();
		}

		return remoteAddress;
	}
}
