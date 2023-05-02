package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.Request;
import http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


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
             OutputStream out = connection.getOutputStream()) {

            DataOutputStream dos = new DataOutputStream(out);
            RequestMapper rm = new RequestMapper
                    ("webapp", RequestMap.getMap(), "UTF-8");
            HttpRequest req = new HttpRequest(in);
            response(dos, rm.getResponse(req));

        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    private void response(DataOutputStream dos, Response rp) {
        try {
            byte[] header = rp.getHeader();
            byte[] body = rp.getBody();
            dos.write(header, 0, header.length);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

}
