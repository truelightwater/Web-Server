package webserver;

import controller.HttpServletController;
import org.reflections.Reflections;
import controller.Controller.urlMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestMap {
    private static final Logger log = LoggerFactory.getLogger(RequestMap.class);
    private static Map<String, HttpServletController> map = new HashMap<>();

    static {
        Class<urlMap> annotation = urlMap.class;
        Reflections reflections = new Reflections("controller");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(annotation);

        for (Class<?> controller : annotated) {
            urlMap urlMap = controller.getAnnotation(annotation);
            try {
                map.put(urlMap.url(), (HttpServletController) controller.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.debug("annotation url mapping error!");
                e.printStackTrace();
            }
        }
    }

    public static Map<String, HttpServletController> getMap() {
        return map;
    }

    public static void setMap(Map<String, HttpServletController> newMap) {
        for (Map.Entry<String, HttpServletController> pair : newMap.entrySet()) {
            map.put(pair.getKey(), pair.getValue());
        }
    }


}
