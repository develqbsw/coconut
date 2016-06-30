package sk.qbsw.core.security.service.ocsp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.util.List;
import java.util.Map;

/**
 * Delegate for HTTPURLConnection. HttpURLConnection must be opened before instantiating delegate.
 * 
 * @author Dalibor Rak
 * @version 1.11.9
 * @since 1.11.9
 */
public class COCSPHttpURLConnection extends HttpURLConnection
{

	/** The connection. */
	private HttpURLConnection connection;

	/* (non-Javadoc)
	 * @see java.net.URLConnection#addRequestProperty(java.lang.String, java.lang.String)
	 */
	public void addRequestProperty(String key, String value) {
		connection.addRequestProperty(key, value);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#connect()
	 */
	public void connect() throws IOException {
		connection.connect();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#disconnect()
	 */
	public void disconnect() {
		connection.disconnect();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return connection.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getAllowUserInteraction()
	 */
	public boolean getAllowUserInteraction() {
		return connection.getAllowUserInteraction();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getConnectTimeout()
	 */
	public int getConnectTimeout() {
		return connection.getConnectTimeout();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getContent()
	 */
	public Object getContent() throws IOException {
		return connection.getContent();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getContent(java.lang.Class[])
	 */
	public Object getContent(@SuppressWarnings("rawtypes") Class[] classes) throws IOException {
		return connection.getContent(classes);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getContentEncoding()
	 */
	public String getContentEncoding() {
		return connection.getContentEncoding();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getContentLength()
	 */
	public int getContentLength() {
		return connection.getContentLength();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getContentLengthLong()
	 */
	public long getContentLengthLong() {
		return connection.getContentLengthLong();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getContentType()
	 */
	public String getContentType() {
		return connection.getContentType();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getDate()
	 */
	public long getDate() {
		return connection.getDate();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getDefaultUseCaches()
	 */
	public boolean getDefaultUseCaches() {
		return connection.getDefaultUseCaches();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getDoInput()
	 */
	public boolean getDoInput() {
		return connection.getDoInput();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getDoOutput()
	 */
	public boolean getDoOutput() {
		return connection.getDoOutput();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getErrorStream()
	 */
	public InputStream getErrorStream() {
		return connection.getErrorStream();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getExpiration()
	 */
	public long getExpiration() {
		return connection.getExpiration();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getHeaderField(int)
	 */
	public String getHeaderField(int n) {
		return connection.getHeaderField(n);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getHeaderField(java.lang.String)
	 */
	public String getHeaderField(String name) {
		return connection.getHeaderField(name);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getHeaderFieldDate(java.lang.String, long)
	 */
	public long getHeaderFieldDate(String name, long Default) {
		return connection.getHeaderFieldDate(name, Default);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getHeaderFieldInt(java.lang.String, int)
	 */
	public int getHeaderFieldInt(String name, int Default) {
		return connection.getHeaderFieldInt(name, Default);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getHeaderFieldKey(int)
	 */
	public String getHeaderFieldKey(int n) {
		return connection.getHeaderFieldKey(n);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getHeaderFieldLong(java.lang.String, long)
	 */
	public long getHeaderFieldLong(String name, long Default) {
		return connection.getHeaderFieldLong(name, Default);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getHeaderFields()
	 */
	public Map<String, List<String>> getHeaderFields() {
		return connection.getHeaderFields();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getIfModifiedSince()
	 */
	public long getIfModifiedSince() {
		return connection.getIfModifiedSince();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		return connection.getInputStream();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getInstanceFollowRedirects()
	 */
	public boolean getInstanceFollowRedirects() {
		return connection.getInstanceFollowRedirects();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getLastModified()
	 */
	public long getLastModified() {
		return connection.getLastModified();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {
		return connection.getOutputStream();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getPermission()
	 */
	public Permission getPermission() throws IOException {
		return connection.getPermission();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getReadTimeout()
	 */
	public int getReadTimeout() {
		return connection.getReadTimeout();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getRequestMethod()
	 */
	public String getRequestMethod() {
		return connection.getRequestMethod();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getRequestProperties()
	 */
	public Map<String, List<String>> getRequestProperties() {
		return connection.getRequestProperties();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getRequestProperty(java.lang.String)
	 */
	public String getRequestProperty(String key) {
		return connection.getRequestProperty(key);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getResponseCode()
	 */
	public int getResponseCode() throws IOException {
		return connection.getResponseCode();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#getResponseMessage()
	 */
	public String getResponseMessage() throws IOException {
		return connection.getResponseMessage();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getURL()
	 */
	public URL getURL() {
		return connection.getURL();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#getUseCaches()
	 */
	public boolean getUseCaches() {
		return connection.getUseCaches();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return connection.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setAllowUserInteraction(boolean)
	 */
	public void setAllowUserInteraction(boolean allowuserinteraction) {
		connection.setAllowUserInteraction(allowuserinteraction);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#setChunkedStreamingMode(int)
	 */
	public void setChunkedStreamingMode(int chunklen) {
		connection.setChunkedStreamingMode(chunklen);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setConnectTimeout(int)
	 */
	public void setConnectTimeout(int timeout) {
		connection.setConnectTimeout(timeout);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setDefaultUseCaches(boolean)
	 */
	public void setDefaultUseCaches(boolean defaultusecaches) {
		connection.setDefaultUseCaches(defaultusecaches);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setDoInput(boolean)
	 */
	public void setDoInput(boolean doinput) {
		connection.setDoInput(doinput);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setDoOutput(boolean)
	 */
	public void setDoOutput(boolean dooutput) {
		connection.setDoOutput(dooutput);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#setFixedLengthStreamingMode(int)
	 */
	public void setFixedLengthStreamingMode(int contentLength) {
		connection.setFixedLengthStreamingMode(contentLength);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#setFixedLengthStreamingMode(long)
	 */
	public void setFixedLengthStreamingMode(long contentLength) {
		connection.setFixedLengthStreamingMode(contentLength);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setIfModifiedSince(long)
	 */
	public void setIfModifiedSince(long ifmodifiedsince) {
		connection.setIfModifiedSince(ifmodifiedsince);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#setInstanceFollowRedirects(boolean)
	 */
	public void setInstanceFollowRedirects(boolean followRedirects) {
		connection.setInstanceFollowRedirects(followRedirects);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setReadTimeout(int)
	 */
	public void setReadTimeout(int timeout) {
		connection.setReadTimeout(timeout);
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#setRequestMethod(java.lang.String)
	 */
	public void setRequestMethod(String method) throws ProtocolException {
		connection.setRequestMethod(method);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setRequestProperty(java.lang.String, java.lang.String)
	 */
	public void setRequestProperty(String key, String value) {
		connection.setRequestProperty(key, value);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#setUseCaches(boolean)
	 */
	public void setUseCaches(boolean usecaches) {
		connection.setUseCaches(usecaches);
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#toString()
	 */
	public String toString() {
		return connection.toString();
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#usingProxy()
	 */
	public boolean usingProxy() {
		return connection.usingProxy();
	}

	/**
	 * Instantiates a new COCSP http url connection.
	 *
	 * @param connection
	 *            the connection
	 */
	public COCSPHttpURLConnection(HttpURLConnection connection) {
		super(connection.getURL());
		this.connection = connection;
		addOCSPRequestProperties();
	}

	/**
	 * Adds the ocsp request properties. (Content-Type ane Accept)
	 */
	public void addOCSPRequestProperties() {
		this.connection.setRequestProperty("Content-Type", "application/ocsp-request");
		this.connection.setRequestProperty("Accept", "application/ocsp-response");
		this.connection.setDoOutput(true);
	}

}
