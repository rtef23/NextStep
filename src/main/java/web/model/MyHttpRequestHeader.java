/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

package web.model;

import java.util.HashMap;
import java.util.Map;

import web.enums.MyHttpMethod;

/**
 * @author 송주용
 */
public class MyHttpRequestHeader {
	private Map<String, String> parameters;
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

	public MyHttpRequestHeader() {
		parameters = new HashMap<>();
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
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
