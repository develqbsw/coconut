/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.LoggerFactory;

/**
 * Class for sending request using http post method and returning response.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>
 * @author Dalibor Rak
 */
final class HttpPost implements Transport
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Sends request (msg attribute) to the specified url and returns response
	 * as a string.
	 *
	 * @param urlString url where to send the request
	 * @param msg 		request message to be sent
	 * @return 			response message
	 * @throws MalformedURLException the malformed url exception
	 */
	public String getResponse (String urlString, String msg) throws MalformedURLException
	{
		URL url = new URL(urlString);
		URLConnection connection;
		StringBuffer response = new StringBuffer();

		try
		{
			if (useProxy)
			{
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyURL, proxyPort));
				connection = url.openConnection(proxy);
			}
			else
			{
				connection = url.openConnection();
			}

			connection.setDoOutput(true);

			/* write request */
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(msg);
			writer.flush();
			writer.close();

			/* read response */
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ( (line = reader.readLine()) != null)
			{
				response.append(line);
			}
			reader.close();

		}
		catch (Throwable ex)
		{
			LoggerFactory.getLogger(HttpPost.class).error("Error during call URL:" + url + " MSG:" + msg, ex);
		}

		/* return response */
		return response.toString();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "instance of HttpPost class";
	}

	/**
	 * Sets the proxy.
	 *
	 * @param useProxy the use proxy
	 * @param proxyURL the proxy url
	 * @param proxyPort the proxy port
	 * @see sk.qbsw.paypal.core.Transport#setProxy(boolean, java.lang.String, java.lang.Integer)
	 */
	public void setProxy (boolean useProxy, String proxyURL, Integer proxyPort)
	{
		this.useProxy = useProxy;
		this.proxyURL = proxyURL;
		this.proxyPort = proxyPort;
	}

	/** The use proxy. */
	private Boolean useProxy = Boolean.FALSE;

	/** The proxy url. */
	private String proxyURL;

	/** The proxy port. */
	private Integer proxyPort;
}
