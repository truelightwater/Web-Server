package http;

import java.io.DataOutputStream;
import java.util.Map;

public class HttpResponse extends Response {

    private DataOutputStream dos = null;

    public HttpResponse(Header header, byte[] body) {
        super(header, body);
    }

    public HttpResponse(Header header, byte[] body, DataOutputStream dos) {
        super(header, body);
        this.dos = dos;
    }

    public void forward(String url, Object responseBody) {

    }

    public static Response sendRedirect(String redirectUrl) {
         return ResponseFactory.get302(redirectUrl, "UTF-8");
    }

    public static Response sendRedirect(String redirectUrl, Map<String,String> cookie) {
        return ResponseFactory.get302(redirectUrl, "UTF-8", cookie);
    }

    private void response200Header() {
        // Response Factory 관련 데이터
        ResponseFactory.get200Html("/index.html", "UTF-8");
    }

    private void responseBody(byte[] body) {

    }


}
