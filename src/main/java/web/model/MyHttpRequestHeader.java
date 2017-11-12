/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.model;

import web.enums.MyHttpMethod;

/**
 * @author 송주용
 */
public class MyHttpRequestHeader {
	private MyHttpMethod httpMethod;
	private String path;
	private String protocol;
	private String host;
	private String connection;
	private String userAgent;
	private String accept;
	private String referer;
	private String acceptEncoding;
	private String acceptLanguage;

	public static MyHttpRequestHeader parseHeader(String headerString) throws IllegalArgumentException {
		MyHttpRequestHeader requestHeader = new MyHttpRequestHeader();
		String[] requestContent = headerString.split("\r\n");

		String[] firstLineElements = requestContent[0].split(" ");

		if (firstLineElements.length != 3) {
			throw new IllegalArgumentException();
		}

		MyHttpMethod method = MyHttpMethod.classifyMethod(firstLineElements[0]);

		requestHeader.setHttpMethod(method);
		requestHeader.setPath(firstLineElements[1]);
		requestHeader.setProtocol(firstLineElements[2]);

		return requestHeader;
	}

	public MyHttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(MyHttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
}
