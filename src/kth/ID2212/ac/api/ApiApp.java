package kth.ID2212.ac.api;

import com.sun.jersey.api.json.JSONConfiguration;
import kth.ID2212.ac.api.filter.JWTTokenNeededFilter;
import kth.ID2212.ac.api.util.AppExceptionMapper;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Configuration of Web Service, here are all endpoints registered and extensions injected.
 */
@ApplicationPath("/api")
public class ApiApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthEndpoint.class);
        classes.add(UsersEndpoint.class);
        classes.add(JacksonFeature.class);
        classes.add(AppExceptionMapper.class);
        classes.add(JWTTokenNeededFilter.class);
        return classes;
    }

    @Override
    public Map<String, Object> getProperties() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
        return props;
    }
}
