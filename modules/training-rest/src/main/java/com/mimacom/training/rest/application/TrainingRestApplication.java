package com.mimacom.training.rest.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.mimacom.training.rest.api.TodosApi;
import org.apache.cxf.common.util.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ntrp
 */
@Component(
        property = {
                "liferay.auth.verifier=false",
                "liferay.oauth2=false",
                JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/training/api/v1",
                JaxrsWhiteboardConstants.JAX_RS_NAME + "=Training.Rest"
        },
        service = Application.class
)
public class TrainingRestApplication extends Application {

    private static final Map<String, String> mimeMap = new HashMap<String,String>() {{
        put("js", "application/javascript");
        put("css", "text/css");
        put("html", "text/html");
        put("png", "image/png");
        put("map", "application/octet-stream");
    }};


    @Reference
    private TodosApi todosApi;

    public Set<Object> getSingletons() {

        Set<Object> singletons = new HashSet<>();
        singletons.add(new JacksonJsonProvider());
        singletons.add(this.todosApi);
        singletons.add(this);

        return singletons;
    }

    @GET
    @Path("/{path:.*}")
    public Response getStatic(@PathParam("path") String path) {

        if (StringUtils.isEmpty(path)) {
            path = "index.html";
        }

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("doc/" + path);
        return Response.ok()
                .type(mimeMap.get(path.replaceAll("[^.]+\\.","")))
                .entity(is)
                .build();
    }
}