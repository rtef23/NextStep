package webserver;

import db.DataBase;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUtil;
import util.HttpRequestUtils;
import util.RegularExpressionUtil;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class RequestHandler extends Thread {
    private static final int METHOD = 0;
    private static final int PATH = 1;
    private static final int PROTOCOL = 2;
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
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body;
            String frontLine = reader.readLine();

            if (StringUtils.isEmpty(frontLine)) {
                throw new IllegalArgumentException();
            }

            String[] frontLineProperties = frontLine.split(" ");

            if (frontLineProperties.length != 3) {
                throw new IllegalArgumentException();
            }

            log.info("[{}] {}", frontLineProperties[METHOD], frontLineProperties[PATH]);

            if (RegularExpressionUtil.isMatch(".html$", frontLineProperties[PATH])) {
                body = FileUtil.readFile("webapp" + frontLineProperties[1]);
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }

            if (RegularExpressionUtil.isMatch(".js$", frontLineProperties[PATH])) {
                body = FileUtil.readFile("webapp" + frontLineProperties[1]);
                response200ScriptHeader(dos, body.length);
                responseBody(dos, body);
                return;
            }
            if (RegularExpressionUtil.isMatch(".css$", frontLineProperties[PATH])) {
                body = FileUtil.readFile("webapp" + frontLineProperties[PATH]);
                response200CssHeader(dos, body.length);
                responseBody(dos, body);
                return;
            }

            int indexOfQMark = frontLineProperties[PATH].indexOf("?");

            if (indexOfQMark >= 0) {
                String queryString = frontLineProperties[PATH].substring(indexOfQMark + 1, frontLineProperties[PATH].length());
                Map<String, String> queries = HttpRequestUtils.parseQueryString(queryString);

                User user = parseQueryToUser(queries);

                DataBase.addUser(user);
            }

            response200Header(dos, 0);
            responseBody(dos, new byte[]{});
        } catch (IOException | IllegalArgumentException e) {
            try {
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                response400Header(dos, 0);
                responseBody(dos, new byte[]{});
            } catch (IOException ioe) {
                log.error(ioe.getMessage());
            }

        }
    }

    private User parseQueryToUser(Map<String, String> queries) {
        String id = queries.get("userId");
        String password = queries.get("password");
        String name = queries.get("name");
        String email = queries.get("email");

        return new User(id, password, name, email);
    }


    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200ScriptHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/javascript;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
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
