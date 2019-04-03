package sk.fri.uniza;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import sk.fri.uniza.auth.ExampleAuthenticator;
import sk.fri.uniza.auth.ExampleAuthorizer;
import sk.fri.uniza.auth.User;
import sk.fri.uniza.health.TemplateHealthCheck;
import sk.fri.uniza.resources.HelloWorldResource;

public class DemoProjectApplication extends Application<DemoProjectConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DemoProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "DemoProject";
    }

    @Override
    public void initialize(final Bootstrap<DemoProjectConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final DemoProjectConfiguration configuration,
                    final Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new ExampleAuthenticator())
                        .setAuthorizer(new ExampleAuthorizer())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }

}
