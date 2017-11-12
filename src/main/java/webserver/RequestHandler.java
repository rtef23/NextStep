package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.FileUtil;
import web.builder.MyHttpRequestBuilder;
import web.manager.ControllerManager;
import web.model.MappingInformation;
import web.model.MyHttpRequest;

public class RequestHandler extends Thread {
	private ControllerManager controllerManager = ControllerManager.getInstance();
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
			connection.getPort());

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			OutputStream out = connection.getOutputStream()) {
			// TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
			MyHttpRequest request = MyHttpRequestBuilder.build(readRequest(reader));
			byte[] body;

			if (StringUtils.equals(request.getMyHttpRequestHeader().getPath(), "/index.html")) {
				body = FileUtil.readFile("src/main/java/webapp/index.html");
			} else {
				body = "Hello, World".getBytes();
			}
//			MappingInformation information = buildMappingInformation(request);
//			
//			Object result = controllerManager.getController(information).execute(request);
//			
//			

			DataOutputStream dos = new DataOutputStream(out);
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			try {
				DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
				response400Header(dos, 0);
				responseBody(dos, new byte[] {});
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			}

		}
	}

	private void processResult(Object result) {
		
	}
	
	private MappingInformation buildMappingInformation(MyHttpRequest request) {
		MappingInformation information = new MappingInformation();

		information.setMethod(request.getMyHttpRequestHeader().getHttpMethod());
		information.setUrlPath(request.getMyHttpRequestHeader().getPath());

		return information;
	}

	private String readRequest(BufferedReader reader) throws IOException {
		StringBuilder builder = new StringBuilder();
		String buffer;

		do {
			buffer = reader.readLine();

			if (StringUtils.isEmpty(buffer)) {
				break;
			}

			builder.append(buffer);
			builder.append("\r\n");
		} while (true);

		return builder.toString();
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response400Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
