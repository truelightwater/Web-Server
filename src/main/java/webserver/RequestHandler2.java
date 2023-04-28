package webserver;

import com.sun.net.httpserver.HttpExchange;
import http.Request;
import http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;


public class RequestHandler2 extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler2.class);
    private Socket connection;

    public RequestHandler2(Socket connectionSocket) {
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
            Request rq = new Request(in);
            response(dos, rm.getResponse(rq));

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
