/**
 * 
 */
package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import util.FileUtil;
import util.HttpResponseUtils;

/**
 * @author 송주용
 *
 */
public class MyHttpResponse {
	private static final String LINE_SEPERATOR = "\r\n";

	private boolean isCookieAdded = false;
	private boolean isFlushed = false;
	private DataOutputStream dataOutputStream;
	private List<MyCookie> cookies;

	public MyHttpResponse() {}

	public MyHttpResponse(DataOutputStream outputStream) {
		this.isCookieAdded = false;
		this.isFlushed = false;
		this.dataOutputStream = outputStream;
	}

	public MyHttpResponse(OutputStream outputStream) {
		this(new DataOutputStream(outputStream));
	}

	public void addCookie(String key, String value) {
		if (cookies == null) {
			cookies = new ArrayList<>();
		}
		isCookieAdded = true;
		cookies.add(new MyCookie(key, value));
	}

	public void addCookie(MyCookie cookie) {
		if (cookies == null) {
			cookies = new ArrayList<>();
		}
		isCookieAdded = true;
		cookies.add(cookie);
	}

	public void send400() throws IOException {
		if (dataOutputStream == null || isFlushed) {
			return;
		}

		int statusCode = 400;

		dataOutputStream.writeBytes(
			String.format("HTTP/1.1 %d %s \r\n", statusCode,
				HttpResponseUtils.responseStatusString(statusCode)));

		dataOutputStream.writeBytes("Content-Type: text/plain;charset=utf-8\r\n");
		dataOutputStream.writeBytes("Content-Length: 0\r\n");
		dataOutputStream.writeBytes(LINE_SEPERATOR);

		isFlushed = true;
		dataOutputStream.flush();

		cookies = new ArrayList<>();
		isCookieAdded = false;
	}
	
	public void send500() throws IOException{
		if (dataOutputStream == null || isFlushed) {
			return;
		}

		int statusCode = 500;

		dataOutputStream.writeBytes(
			String.format("HTTP/1.1 %d %s \r\n", statusCode,
				HttpResponseUtils.responseStatusString(statusCode)));

		dataOutputStream.writeBytes("Content-Type: text/plain;charset=utf-8\r\n");
		dataOutputStream.writeBytes("Content-Length: 0\r\n");
		dataOutputStream.writeBytes(LINE_SEPERATOR);

		isFlushed = true;
		dataOutputStream.flush();

		cookies = new ArrayList<>();
		isCookieAdded = false;
	}

	public void sendRedirect(String redirectUri) throws IOException {
		if (dataOutputStream == null || isFlushed) {
			return;
		}

		int statusCode = 302;

		dataOutputStream.writeBytes(
			String.format("HTTP/1.1 %d %s \r\n", statusCode,
				HttpResponseUtils.responseStatusString(statusCode)));

		dataOutputStream.writeBytes(String.format("Location: %s\r\n", redirectUri));
		dataOutputStream.writeBytes("Content-Type: text/plain;charset=utf-8\r\n");

		if (isCookieAdded) {
			dataOutputStream.writeBytes(String.format("Set-Cookie: %s\r\n", buildCookieString()));
		}

		dataOutputStream.writeBytes("Content-Length: 0\r\n");
		dataOutputStream.writeBytes(LINE_SEPERATOR);

		isFlushed = true;
		dataOutputStream.flush();

		cookies = new ArrayList<>();
		isCookieAdded = false;
	}

	private String buildCookieString() {
		StringBuilder buffer = new StringBuilder();

		for (MyCookie cookie : cookies) {
			buffer.append(cookie.getKey());
			buffer.append("=");
			buffer.append(cookie.getValue());
		}

		return buffer.toString();
	}

	public void sendForward(String forwardFileName) throws IOException {
		if (dataOutputStream == null || isFlushed) {
			return;
		}

		byte[] body = null;

		try {
			body = FileUtil.readFile("webapp" + forwardFileName);
		} catch (IOException e) {
			send404();
		}

		int statusCode = 200;

		dataOutputStream.writeBytes(
			String.format("HTTP/1.1 %d %s \r\n", statusCode, HttpResponseUtils.responseStatusString(statusCode)));

		if (forwardFileName.endsWith(".html")) {
			dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
		} else if (forwardFileName.endsWith(".js")) {
			dataOutputStream.writeBytes("Content-Type: text/javascript;charset=utf-8\r\n");
		} else if (forwardFileName.endsWith(".css")) {
			dataOutputStream.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
		}

		if (isCookieAdded) {
			dataOutputStream.writeBytes(String.format("Set-Cookie: %s\r\n", buildCookieString()));
		}

		dataOutputStream.writeBytes(String.format("Content-Length: %d\r\n", body.length));
		dataOutputStream.writeBytes(LINE_SEPERATOR);

		dataOutputStream.write(body, 0, body.length);

		isFlushed = true;
		dataOutputStream.flush();

		cookies = new ArrayList<>();
		isCookieAdded = false;
	}

	public void sendForward(byte[] body) throws IOException {
		if (dataOutputStream == null || isFlushed) {
			return;
		}

		int statusCode = 200;

		dataOutputStream.writeBytes(
			String.format("HTTP/1.1 %d %s \r\n", statusCode, HttpResponseUtils.responseStatusString(statusCode)));

		dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");

		if (isCookieAdded) {
			dataOutputStream.writeBytes(String.format("Set-Cookie: %s\r\n", buildCookieString()));
		}

		dataOutputStream.writeBytes(String.format("Content-Length: %d\r\n", body.length));
		dataOutputStream.writeBytes(LINE_SEPERATOR);

		dataOutputStream.write(body, 0, body.length);

		isFlushed = true;
		dataOutputStream.flush();

		cookies = new ArrayList<>();
		isCookieAdded = false;
	}

	public void send404() throws IOException {
		if (dataOutputStream == null || isFlushed) {
			return;
		}

		int statusCode = 404;

		dataOutputStream.writeBytes(
			String.format("HTTP/1.1 %d %s \r\n", statusCode, HttpResponseUtils.responseStatusString(statusCode)));

		dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");

		if (isCookieAdded) {
			dataOutputStream.writeBytes(String.format("Set-Cookie: %s\r\n", buildCookieString()));
		}

		dataOutputStream.writeBytes("Content-Length: 0\r\n");
		dataOutputStream.writeBytes(LINE_SEPERATOR);

		isFlushed = true;
		dataOutputStream.flush();

		cookies = new ArrayList<>();
		isCookieAdded = false;
	}
}
