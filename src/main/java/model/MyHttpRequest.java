/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.google.common.collect.Maps;

import util.HttpRequestUtils;
import util.HttpRequestUtils.Pair;
import util.IOUtils;
import util.MyStringUtils;

/**
 * @author 송주용
 *
 */
public class MyHttpRequest {
	private static final int METHOD = 0;
	private static final int PATH = 1;
	private static final int PROTOCOL = 2;

	private static final String GET = "GET";
	private static final String POST = "POST";

	private Map<String, String> header;
	private String body;

	private String method;
	private String path;
	private String protocol;
	private Map<String, String> parameters;
	private Map<String, String> cookies;

	public MyHttpRequest(InputStream inputStream) throws IOException, IllegalArgumentException {
		this(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
	}

	public MyHttpRequest(BufferedReader reader) throws IOException, IllegalArgumentException {
		if (reader == null) {
			throw new IOException();
		}

		String line = reader.readLine();
		String firstLineElements[] = line.split(" ");

		if (firstLineElements == null || firstLineElements.length != 3) {
			throw new IllegalArgumentException();
		}

		Map<String, String> header = Maps.newHashMap();

		method = firstLineElements[METHOD];
		path = firstLineElements[PATH];
		protocol = firstLineElements[PROTOCOL];

		if (MyStringUtils.isAnyEmpty(method, path, protocol)) {
			throw new IllegalArgumentException();
		}

		while ((line = reader.readLine()) != null) {
			Pair headerProperty = HttpRequestUtils.parseHeader(line);

			if (headerProperty == null) {
				break;
			}

			header.put(headerProperty.getKey(), headerProperty.getValue());
		}

		if (header.containsKey("Cookie")) {
			String cookieString = header.get("Cookie");

			this.cookies = HttpRequestUtils.parseCookies(cookieString);
		}

		this.header = header;

		if (MyStringUtils.isEqual(method, GET)) {
			int indexOfQMark = path.indexOf("?");

			if (GET.equals(method) && indexOfQMark >= 0) {
				String queryString = path.substring(indexOfQMark + 1, path.length());
				this.parameters = HttpRequestUtils.parseQueryString(queryString);

				path = path.replace(queryString, "").replace("?", "");
			}

			return;
		}

		if (MyStringUtils.isEqual(method, POST)) {
			String contentLengthString = header.get("Content-Length");

			if (MyStringUtils.isAnyEmpty(contentLengthString)) {
				return;
			}

			int contentLength = Integer.parseInt(contentLengthString);
			String bodyString = IOUtils.readData(reader, contentLength);

			this.body = bodyString;
			this.parameters = HttpRequestUtils.parseQueryString(bodyString);
		}
	}

	public MyHttpRequest() {
		this.header = Maps.newHashMap();
	}

	public String getHeader(String key) {
		return header.get(key);
	}

	public void setHeader(String key, String value) {
		this.header.put(key, value);
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

	public void setParameter(String key, String value) {
		this.parameters.put(key, value);
	}

	public String getCookie(String key) {
		return cookies.get(key);
	}
}
