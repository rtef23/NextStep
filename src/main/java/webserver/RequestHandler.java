package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import http.HttpSessions;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	private static final String SET_COOKIE_NAME = "Set-Cookie";
	private static final String SESSION_COOKIE_NAME = "JSESSIONID";

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			HttpRequest request = new HttpRequest(in);
			HttpResponse response = new HttpResponse(out);

			Controller controller = RequestMapping.getController(request.getPath());
			if (controller == null) {
				String path = getDefaultPath(request.getPath());
				response.forward(path);
			} else {
				String sessionId = request.getCookie(SESSION_COOKIE_NAME);

				HttpSession session;
				if (HttpSessions.isExistSessionId(sessionId)) {
					log.debug("#####   session id exist {} ", sessionId);
					session = HttpSessions.getSession(sessionId);
				} else {
					log.debug("#####   session id not exist {} ", sessionId);
					sessionId = UUID.randomUUID().toString();

					session = new HttpSession(sessionId);
					HttpSessions.addSession(sessionId, session);

				}

				request.setSession(session);
				request.removeCookie(SESSION_COOKIE_NAME);

				response.addHeader(SET_COOKIE_NAME, SESSION_COOKIE_NAME + "=" + session.getId());

				controller.service(request, response);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private String getDefaultPath(String path) {
		if (path.equals("/")) {
			return "/index.html";
		}
		return path;
	}
}
