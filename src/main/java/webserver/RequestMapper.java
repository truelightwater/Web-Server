package webserver;

import controller.HttpServletController;
import http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class RequestMapper {
    String root;
    String encoding;
    Map<String, HttpServletController> requestMapping;

    public RequestMapper(String root, Map<String, HttpServletController> requestMapping, String encoding) {
        this.root = root;
        this.encoding = encoding;
        this.requestMapping = requestMapping;
    }

    public Response getResponse(HttpRequest req) {
        HttpServletController httpServletController;

        // url 이 requestMapping 에 있을 경우 httpServletController 연결
        if (this.requestMapping != null && (httpServletController = requestMapping.get(req.getFileName())) != null) {
            return httpServletController.service(req);

        } else {
            try {
                byte[] body = Files.readAllBytes(Paths.get(root + req.getFilePath()));
                Header header = Header.Builder.n()
                        .statusCode("200")
                        .contentType(req.getAccept())
                        .encoding(encoding)
                        .length(body.length)
                        .build();

                return new Response(header,body);

            } catch (IOException e) {
                //파일 없으면 404
                return ResponseFactory.get404Html("UTF-8");
            }
        }
    }
}
