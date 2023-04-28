package controller;

import http.Request;
import http.Response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Controller {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface urlMap {
        String url();
    }

    Response service(Request rq);
}
