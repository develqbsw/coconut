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
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.LoggerFactory;

/**
 * Class for sending request using http post method and returning response.
 *
 * @author Dalibor Rak
 * @version 1.0
 */
final class HttpsPost implements Transport
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The proxy port. */
	private Integer proxyPort = 3128;

	/** The proxy url. */
	private String proxyURL = "192.168.121.21";

	/** The use proxy. */
	private Boolean useProxy = Boolean.FALSE;

	/**
	 * Sends request (message attribute) to the specified URL and returns response
	 * as a string.
	 *
	 * @param urlString URL where to send the request
	 * @param msg request message to be sent
	 * @return response message
	 * @throws MalformedURLException the malformed URL exception
	 */
	public String getResponse (String urlString, String msg) throws MalformedURLException
	{

		/* write request */
		TrustManager[] trustManagers = new TrustManager[] {new X509TrustManager()
		{
			public void checkClientTrusted (X509Certificate[] certs, String authType)
			{
			}

			public void checkServerTrusted (X509Certificate[] certs, String authType)
			{
			}

			public java.security.cert.X509Certificate[] getAcceptedIssuers ()
			{
				return new X509Certificate[]{};
			}

		}};

		URL url = new URL(urlString);
		URLConnection connection;
		StringBuilder response = new StringBuilder();

		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustManagers, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			if (useProxy && proxyURL != null && proxyPort > 0)
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
		catch (Exception ex)
		{
			LoggerFactory.getLogger(HttpsPost.class).error("Error during call URL:" + url + " MSG:" + msg, ex);
		}

		/* return response */
		return response.toString();
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

	/**
	 * To string.
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "instance of HttpsPost class :" + this;
	}

}
