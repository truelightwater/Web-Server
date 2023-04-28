package webserver;

import controller.Controller;
import http.Header;
import http.Request;
import http.Response;
import http.ResponseFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class RequestMapper {
    String root;
    String encoding;
    Map<String, Controller> requestMapping;

    public RequestMapper(String root, String encoding) {
        this(root, null, encoding);
    }

    public RequestMapper(String root, Map<String, Controller> requestMapping, String encoding) {
        this.root = root;
        this.encoding = encoding;
        this.requestMapping = requestMapping;
    }

    public Response getResponse(Request rq) {
        Controller controller;

        // invalid request -> 400 Page
        if (!rq.isValid()) {
             return ResponseFactory.get400Html("UTF-8");
        }

        //url 이 requestMapping 에 있을 경우 controller 연결
        if(this.requestMapping != null && (controller = requestMapping.get(rq.getFileName())) != null) {
            return controller.service(rq);

        } else { // url 가 requestMapping 에 없을 경우 파일 연결
            try {
                byte[] body = Files.readAllBytes(Paths.get(root + rq.getFilePath()));
                Header header = Header.Builder.n()
                        .statusCode("200")
                        .contentType(rq.getAccept())
                        .encoding(encoding)
                        .length(body.length)
                        .build();

                return new Response(header, body);

            } catch (IOException e) {
                return ResponseFactory.get404Html("UTF-8");
            }
        }
    }
}
