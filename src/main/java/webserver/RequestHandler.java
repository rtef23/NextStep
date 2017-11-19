package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import db.DataBase;
import model.User;
import model.UserLoginInfo;
import model.mHttpRequest;
import util.FileUtil;
import util.HttpRequestUtils;
import util.HttpRequestUtils.Pair;
import util.HttpResponseUtils;
import util.IOUtils;
import util.mStringUtils;

public class RequestHandler extends Thread {
	private static final int METHOD = 0;
	private static final int PATH = 1;
	private static final int PROTOCOL = 2;

	private static final String LINE_SEPERATOR = "\r\n";
	private static final String BODY_VALUE_SEPERATOR = "&";

	private static final String GET = "GET";
	private static final String POST = "POST";

	private static final String TEXT_PLAIN = "text/plain";
	private static final String TEXT_HTML = "text/html";
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String TEXT_CSS = "text/css";

	private static final String SET_COOKIE = "Set-Cookie";
	private static final String CONTENT_TYPE = "contentType";
	private static final String LOCATION = "location";

	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());
		Map<String, String> responseHeaderProperties = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {

			try {
				mHttpRequest request = parseRequest(reader);
				byte[] body;
				String method = request.getHeader().get("method");
				String path = request.getHeader().get("path");

				switch (method) {
					case GET: {
						if (path.endsWith(".html")) {
							body = FileUtil.readFile("webapp" + path);
							responseHeaderProperties.put(CONTENT_TYPE, TEXT_HTML);
							responseHeader(200, responseHeaderProperties, dos, body.length);
							responseBody(dos, body);
							return;
						}

						if (path.endsWith(".js")) {
							body = FileUtil.readFile("webapp" + path);
							responseHeaderProperties.put(CONTENT_TYPE, TEXT_JAVASCRIPT);
							responseHeader(200, responseHeaderProperties, dos, body.length);
							responseBody(dos, body);
							return;
						}
						if (path.endsWith(".css")) {
							body = FileUtil.readFile("webapp" + path);
							responseHeaderProperties.put(CONTENT_TYPE, TEXT_CSS);
							responseHeader(200, responseHeaderProperties, dos, body.length);
							responseBody(dos, body);
							return;
						}

						if (path.equals("/user/list")) {
							for (String s : request.getHeader().keySet()) {
								log.info("key : {}\tvalue : {}", s, request.getHeader().get(s));
							}

							String cookieString = request.getHeader().get("Cookie");

							if (mStringUtils.isAnyEmpty(cookieString)) {
								responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
								responseHeaderProperties.put(LOCATION, "/index.html");
								responseHeader(302, responseHeaderProperties, dos, 0);

								responseBody(dos, new byte[] {});
								return;
							}

							Map<String, String> parsedCookies = HttpRequestUtils.parseCookies(cookieString);

							if (mStringUtils.isEqual("true", parsedCookies.get("logined")) == false) {
								responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
								responseHeaderProperties.put(LOCATION, "/index.html");
								responseHeader(302, responseHeaderProperties, dos, 0);

								responseBody(dos, new byte[] {});
								return;
							}

							Collection<User> users = DataBase.findAll();
							StringBuilder buffer = new StringBuilder();

							buffer.append("<table>");

							for (User user : users) {
								buffer.append("<tr>");

								buffer.append("<td>" + user.getUserId() + "</td>");
								buffer.append("<td>" + user.getName() + "</td>");
								buffer.append("<td>" + user.getEmail() + "</td>");

								buffer.append("</tr>");
							}

							buffer.append("</table>");

							byte[] bufferByte = buffer.toString().getBytes();

							responseHeaderProperties.put(CONTENT_TYPE, TEXT_HTML);
							responseHeader(200, responseHeaderProperties, dos, bufferByte.length);

							responseBody(dos, bufferByte);
							return;
						}

						if (path.equals("/user/create")) {
							log.info("[{}] {}", method, path);

							int indexOfQMark = path.indexOf("?");

							if (indexOfQMark >= 0) {
								String queryString = path.substring(indexOfQMark + 1, path.length());
								Map<String, String> queries = HttpRequestUtils.parseQueryString(queryString);

								path = path.replace(queryString, "").replace("?", "");
								request.getHeader().put("path", path);

								User user = parseDataToUser(queries);

								DataBase.addUser(user);

								responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
								responseHeaderProperties.put(LOCATION, "/index.html");
								responseHeader(302, responseHeaderProperties, dos, 0);

								responseBody(dos, new byte[] {});
								return;
							}
						}

						break;
					}
					case POST: {
						if (path.equals("/user/create")) {
							log.info("[{}] {}", method, path);

							User user = parseDataToUser(request.getBody());

							DataBase.addUser(user);

							responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
							responseHeaderProperties.put(LOCATION, "/index.html");
							responseHeader(302, responseHeaderProperties, dos, 0);

							responseBody(dos, new byte[] {});
							return;
						}

						if (path.equals("/user/login")) {
							log.info("[{}] {}", method, path);

							UserLoginInfo requestUser = parseDataToLoginInfo(request.getBody());
							User targetUser = DataBase.findUserById(requestUser.getUserId());

							if (targetUser == null
								|| mStringUtils.isAnyEmpty(requestUser.getPassword(), targetUser.getPassword())) {
								responseHeaderProperties.put(SET_COOKIE, "logined=false");
								responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
								responseHeaderProperties.put(LOCATION, "/user/login_failed.html");
								responseHeader(302, responseHeaderProperties, dos, 0);

								responseBody(dos, new byte[] {});
								return;
							}

							responseHeaderProperties.put(SET_COOKIE, "logined=true");
							responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
							responseHeaderProperties.put(LOCATION, "/index.html");
							responseHeader(302, responseHeaderProperties, dos, 0);

							responseBody(dos, new byte[] {});
							return;
						}

						break;
					}
					default:
						throw new IllegalArgumentException();
				}

				responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
				responseHeader(404, responseHeaderProperties, dos, 0);
				responseBody(dos, new byte[] {});
				return;
			} catch (IOException | IllegalArgumentException e) {
				log.error("on body1");
				log.error(e.getMessage());
				responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
				responseHeader(400, responseHeaderProperties, dos, 0);
				responseBody(dos, new byte[] {});
			} catch (Exception e) {
				log.error("on body2");
				log.error(e.getMessage());
				responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
				responseHeader(500, responseHeaderProperties, dos, 0);
				responseBody(dos, new byte[] {});
			}
		} catch (Exception e) {
			log.error("on body3");
			log.error(e.getMessage());
		}

	}

	private mHttpRequest parseRequest(BufferedReader reader) throws IOException {
		if (reader == null) {
			throw new IOException();
		}

		mHttpRequest parsedRequest = new mHttpRequest();

		String line = reader.readLine();
		String firstLineElements[] = line.split(" ");

		if (firstLineElements == null || firstLineElements.length != 3) {
			throw new IllegalArgumentException();
		}

		Map<String, String> header = Maps.newHashMap();

		header.put("method", firstLineElements[METHOD]);
		header.put("path", firstLineElements[PATH]);
		header.put("protocol", firstLineElements[PROTOCOL]);

		if (mStringUtils.isAnyEmpty(header.get("method"), header.get("path"), header.get("protocol"))) {
			throw new IllegalArgumentException();
		}

		while (reader.ready()) {
			line = reader.readLine();
			Pair headerProperty = HttpRequestUtils.parseHeader(line);

			if (headerProperty == null) {
				break;
			}

			header.put(headerProperty.getKey(), headerProperty.getValue());
		}

		parsedRequest.setHeader(header);

		if (mStringUtils.isEqual(header.get("method"), GET)) {
			return parsedRequest;
		}

		String contentLengthString = header.get("Content-Length");

		if (mStringUtils.isAnyEmpty(contentLengthString)) {
			throw new IllegalArgumentException();
		}

		int contentLength = Integer.parseInt(contentLengthString);
		String bodyString = IOUtils.readData(reader, contentLength);

		Map<String, String> body = HttpRequestUtils.parseValues(bodyString, BODY_VALUE_SEPERATOR);

		parsedRequest.setBody(body);

		return parsedRequest;
	}

	private UserLoginInfo parseDataToLoginInfo(Map<String, String> loginData) throws IllegalArgumentException {
		String id = loginData.get("userId");
		String password = loginData.get("password");

		if (mStringUtils.isAnyEmpty(id, password)) {
			throw new IllegalArgumentException();
		}

		return new UserLoginInfo(id, password);
	}

	private User parseDataToUser(Map<String, String> userData) throws IllegalArgumentException {
		String id = userData.get("userId");
		String password = userData.get("password");
		String name = userData.get("name");
		String email = userData.get("email");

		if (mStringUtils.isAnyEmpty(id, password, name, email)) {
			throw new IllegalArgumentException();
		}

		return new User(id, password, name, email);
	}

	private void responseHeader(int statusCode, Map<String, String> headerProperties, DataOutputStream dos,
		int lengthOfBodyContent) {
		try {
			dos.writeBytes(
				String.format("HTTP/1.1 %d %s \r\n", statusCode, HttpResponseUtils.responseStatusString(statusCode)));

			if (statusCode / 100 == 3 && headerProperties.containsKey(LOCATION)) {
				dos.writeBytes(String.format("Location: %s\r\n", headerProperties.get(LOCATION)));
			}

			if (headerProperties.containsKey(CONTENT_TYPE)) {
				dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", headerProperties.get(CONTENT_TYPE)));
			} else {
				dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", TEXT_PLAIN));
			}

			if (headerProperties.containsKey(SET_COOKIE)) {
				dos.writeBytes(String.format("%s: %s\r\n", SET_COOKIE, headerProperties.get(SET_COOKIE)));
			}

			dos.writeBytes("Content-Length: " + lengthOfBodyContent + LINE_SEPERATOR);
			dos.writeBytes(LINE_SEPERATOR);
		} catch (IOException e) {
			log.error("on writing Response Header");
			log.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error("on writing Reponse Body");
			log.error(e.getMessage());
		}
	}
}
