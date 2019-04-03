package sk.fri.uniza.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import sk.fri.uniza.api.Saying;
import sk.fri.uniza.auth.Roles;
import sk.fri.uniza.auth.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    @Path("/hello-world")
    @RolesAllowed({Roles.ADMIN,Roles.READ_ONLY})
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET
    @Timed
    @Path("/hello-user")
    @RolesAllowed({Roles.ADMIN})
    public Saying sayHello(@Auth User user) {
        final String value = user.getUserName();
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET
    @Path("/hello-optional")
    @PermitAll
    public String getGreeting(@Auth Optional<User> userOpt) {
        if (userOpt.isPresent()) {
            return "Hello, " + userOpt.get().getUserName() + "!";
        } else {
            return "Greetings, anonymous visitor!";
        }
    }
}