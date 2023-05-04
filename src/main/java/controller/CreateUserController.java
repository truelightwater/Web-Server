package controller;

import db.DataBase;
import http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;

@HttpServletController.urlMap(url = "create")
public class CreateUserController implements HttpServletController {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public Response service(HttpRequest request) {
        String method = request.getMethod();

        if (method.equals("GET")) {
            return doGet(request);

        } else if (method.equals("POST")) {
            return doPost(request);
        }

        return null;
    }

    @Override
    public Response doGet(HttpRequest request) {
        Map<String, String> params = request.getParams();

        log.debug("CreateUserController GET Method, param : {} ", params);

        if (params != null) {
            String userId = params.get("userId");
            String password = params.get("password");
            String name = params.get("name");
            String email = params.get("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            log.debug("added {} ", user);

            return HttpResponse.sendRedirect("/index.html");
        } else {
            ResponseFactory.get404Html("UTF-8");
        }
        return null;
    }

    @Override
    public Response doPost(HttpRequest request) {
        Map<String, String> params = request.getParams();

        log.debug("CreateUserController POST Method, param : {} ", params);

        if (params != null) {
            String userId = params.get("userId");
            String password = params.get("password");
            String name = params.get("name");
            String email = params.get("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            log.debug("added {} ", user);

            return HttpResponse.sendRedirect("/index.html");
        } else {
            ResponseFactory.get404Html("UTF-8");
        }
        return null;

    }

}
