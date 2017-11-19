package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.AbstractController;
import model.MyHttpRequest;
import model.MyHttpResponse;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (InputStream inputStream = connection.getInputStream();
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
			MyHttpResponse response = new MyHttpResponse(dos);

			try {
				MyHttpRequest request = new MyHttpRequest(inputStream);
				String path = request.getPath();

				if (path.endsWith(".html") || path.endsWith(".js") || path.endsWith(".css")) {
					ControllerManager.getController("default").service(request, response);
					return;
				}

				AbstractController controller = ControllerManager.getController(request.getPath());

				if (controller == null) {
					response.send404();
					return;
				}

				controller.service(request, response);
			} catch (IOException | IllegalArgumentException e) {
				log.error(e.getMessage());
				response.send400();
			} catch (Exception e) {
				log.error(e.getMessage());
				response.send500();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
