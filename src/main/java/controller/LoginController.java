package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.Response;
import http.ResponseFactory;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.HashMap;
import java.util.Map;

@HttpServletController.urlMap(url="login")
public class LoginController implements HttpServletController {

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

        log.debug("loginController, param : {} ", params);

        if (params != null) {
            String userId = params.get("userId");
            String password = params.get("password");
            User user = DataBase.findUserById(userId);

            if (user == null || !user.getPassword().equals(password)) {
                return ResponseFactory.get302("/login_failed.html", "UTF-8");
            }

            log.debug("{} logged in.", user.getUserId());

            Map<String, String> cookie = new HashMap<>();
            cookie.put("logged", "true");

            return HttpResponse.sendRedirect("/index.html", cookie);
        } else {
            return ResponseFactory.get404Html("UTF-8");
        }
    }

    @Override
    public Response doPost(HttpRequest request) {
        Map<String, String> params = request.getParams();

        log.debug("loginController, param : {} ", params);

        if (params != null) {
            String userId = params.get("userId");
            String password = params.get("password");
            User user = DataBase.findUserById(userId);

            if (user == null || !user.getPassword().equals(password)) {
                return ResponseFactory.get302("/login_failed.html", "UTF-8");
            }

            log.debug("{} logged in.", user.getUserId());

            Map<String, String> cookie = new HashMap<>();
            cookie.put("logged", "true");

            return HttpResponse.sendRedirect("/index.html", cookie);

        } else {
            return ResponseFactory.get404Html("UTF-8");
        }
    }
}
