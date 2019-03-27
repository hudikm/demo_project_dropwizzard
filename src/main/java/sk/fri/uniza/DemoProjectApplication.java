package sk.fri.uniza;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }

}
