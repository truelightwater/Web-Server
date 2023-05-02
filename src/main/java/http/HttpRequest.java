package http;

import java.io.InputStream;

public class HttpRequest extends Request {
    public HttpRequest(InputStream in) {
        super(in);
    }
}
