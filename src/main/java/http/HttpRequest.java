package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import util.HttpRequestUtils;
import util.IOUtils;
import util.StringUtils;

public class HttpRequest {
	private final String COOKIE_NAME = "Cookie";

	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private Map<String, String> cookies;

	private HttpSession session;

	private RequestLine requestLine;

	private HttpHeaders headers;

	private RequestParams requestParams = new RequestParams();

	public HttpRequest(InputStream is) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			requestLine = new RequestLine(createRequestLine(br));
			requestParams.addQueryString(requestLine.getQueryString());
			headers = processHeaders(br);

			String cookieValues = headers.getHeader(COOKIE_NAME);

			if (StringUtils.isEmpty(cookieValues) == false) {
				cookies = HttpRequestUtils.parseCookies(cookieValues);
			} else {
				cookies = Maps.newHashMap();
			}

			requestParams.addBody(IOUtils.readData(br, headers.getContentLength()));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private String createRequestLine(BufferedReader br) throws IOException {
		String line = br.readLine();
		if (line == null) {
			throw new IllegalStateException();
		}
		return line;
	}

	private HttpHeaders processHeaders(BufferedReader br) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		String line;
		while (!(line = br.readLine()).equals("")) {
			headers.add(line);
		}
		return headers;
	}

	public HttpMethod getMethod() {
		return requestLine.getMethod();
	}

	public String getPath() {
		return requestLine.getPath();
	}

	public String getHeader(String name) {
		return headers.getHeader(name);
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getParameter(String name) {
		return requestParams.getParameter(name);
	}

	/**
	 * @return the cookies
	 */
	public String getCookie(String key) {
		return cookies.get(key);
	}

	public void removeCookie(String key) {
		cookies.remove(key);
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void addCookie(Cookie cookie) {
		this.cookies.put(cookie.getKey(), cookie.getValue());
	}
}
