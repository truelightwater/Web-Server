package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String requestLine = reader.readLine(); // 첫 번째 라인 HTTP 요청 라인
            String[] requestLineToken = requestLine.split(" ");
            String method = requestLineToken[0];
            String path = requestLineToken[1];
            String version = requestLineToken[2];
            log.debug("HTTP Request Method : {}, Path : {}, version : {}", method, path, version);

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            if ("/".equals(path)) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = "Hello World".getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

            // 회원가입 페이지
            if ("/user/form.html".equals(path)) {
                File file = new File("webapp/user/form.html");

                if (file.exists()) {
                    byte[] body = Files.readAllBytes(file.toPath());
                    DataOutputStream dos = new DataOutputStream(out);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
            }

            // 로그인 페이지
            if ("/user/login.html".equals(path)) {
                File file = new File("webapp/user/login.html");
                if (file.exists()) {
                    byte[] body = Files.readAllBytes(file.toPath());
                    DataOutputStream dos = new DataOutputStream(out);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
            }

            // 사용자 목록 페이지
            if ("/user/list.html".equals(path)) {
                File file = new File("webapp/user/list.html");
                if (file.exists()) {
                    byte[] body = Files.readAllBytes(file.toPath());
                    DataOutputStream dos = new DataOutputStream(out);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }
            }

            // index.html
            if ("/index.html".equals(path)) {
                File file = new File("webapp/index.html");
                if (file.exists()) {
                    byte[] body = Files.readAllBytes(file.toPath());
                    DataOutputStream dos = new DataOutputStream(out);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                } else {
                    // 파일이 존재하지 않을 경우 404 Not Found 응답
                    byte[] body = "Not Found index.html".getBytes();
                    DataOutputStream dos = new DataOutputStream(out);
                    response404Header(dos, body.length);
                    responseBody(dos, body);
                }
            } else {
                // 지원하지 않는 요청일 경우 404 Not Found 응답
                byte[] body = "Not Found".getBytes();
                DataOutputStream dos = new DataOutputStream(out);
                response404Header(dos, body.length);
                responseBody(dos, body);
            }

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

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
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
