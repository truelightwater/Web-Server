package controller;

import http.HttpRequest;
import http.Response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface HttpServletController {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface urlMap {
        String url();
    }

    Response service(HttpRequest request);

    Response doGet(HttpRequest request);

    Response doPost(HttpRequest request);

}
