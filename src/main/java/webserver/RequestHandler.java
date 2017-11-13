package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import util.FileUtil;
import util.HttpRequestUtils;
import util.HttpResponseUtils;
import util.mStringUtils;

public class RequestHandler extends Thread {
	private static final int METHOD = 0;
	private static final int PATH = 1;
	private static final int PROTOCOL = 2;

	private static final String TEXT_PLAIN = "text/plain";
	private static final String TEXT_HTML = "text/html";
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String TEXT_CSS = "text/css";

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
				byte[] body;
				String frontLine = reader.readLine();

				if (mStringUtils.isAnyEmpty(frontLine)) {
					throw new IllegalArgumentException();
				}

				String[] lineElements = frontLine.split(" ");

				if (lineElements.length != 3) {
					throw new IllegalArgumentException();
				}

				String method = lineElements[METHOD];
				String path = lineElements[PATH];
				String protocol = lineElements[PROTOCOL];

				if (mStringUtils.isAnyEmpty(method, path, protocol)) {
					throw new IllegalArgumentException();
				}

				log.info("[{}] {}", method, path);

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

				int indexOfQMark = path.indexOf("?");

				if (indexOfQMark >= 0) {
					String queryString = path.substring(indexOfQMark + 1, path.length());
					Map<String, String> queries = HttpRequestUtils.parseQueryString(queryString);
					path = path.replace(queryString, "").replace("?", "");

					if (path.equals("/user/create")) {
						User user = parseQueryToUser(queries);

						DataBase.addUser(user);

						responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
						responseHeaderProperties.put(LOCATION, "/index.html");
						responseHeader(302, responseHeaderProperties, dos, 0);
						responseBody(dos, new byte[] {});
						return;
					}
				}

				responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
				responseHeader(200, responseHeaderProperties, dos, 0);
				responseBody(dos, new byte[] {});
				return;
			} catch (IOException | IllegalArgumentException e) {
				log.error(e.getMessage());
				responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
				responseHeader(400, responseHeaderProperties, dos, 0);
				responseBody(dos, new byte[] {});
			} catch (Exception e) {
				log.error(e.getMessage());
				responseHeaderProperties.put(CONTENT_TYPE, TEXT_PLAIN);
				responseHeader(500, responseHeaderProperties, dos, 0);
				responseBody(dos, new byte[] {});
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	private User parseQueryToUser(Map<String, String> queries) throws IllegalArgumentException {
		String id = queries.get("userId");
		String password = queries.get("password");
		String name = queries.get("name");
		String email = queries.get("email");

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

			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
