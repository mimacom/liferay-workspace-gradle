package com.mimacom.training.rest.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.mimacom.training.rest.api.PetsApi;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ntrp
 */
@Component(
        property = {
                "liferay.auth.verifier=false",
                "liferay.oauth2=false",
                JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/training-api/v1",
                JaxrsWhiteboardConstants.JAX_RS_NAME + "=Training.Rest"
        },
        service = Application.class
)
public class TrainingRestApplication extends Application {

    @Reference
    private PetsApi petsApi;

    public Set<Object> getSingletons() {

        Set<Object> singletons = new HashSet<>();
        singletons.add(new JacksonJsonProvider());
        singletons.add(this.petsApi);
        singletons.add(this);

        return singletons;
    }

    @GET
    @Produces("text/plain")
    public String working() {
        return "Training API";
    }
}