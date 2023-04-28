package controller;

import db.DataBase;
import http.Request;
import http.Response;
import http.ResponseFactory;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;

@Controller.urlMap(url="create")
public class CreateUser implements Controller {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public Response service(Request rq) {
        Map<String, String> params = rq.getParams();
        log.debug("controller userController, param : {} ", params);

        if (params != null) {
            String userId = params.get("userId");
            String password = params.get("password");
            String name = params.get("name");
            String email = params.get("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            log.debug("added {} ", user);

            return ResponseFactory.get302("/index.html", "UTF-8");
        } else {
            return ResponseFactory.get404Html("UTF-8");
        }
    }
}