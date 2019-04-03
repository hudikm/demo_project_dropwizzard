package sk.fri.uniza.auth;

import io.dropwizard.auth.Authorizer;

public class ExampleAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        if (user.getRoles() == null) return false;
        return user.getRoles().contains(role);
    }
}
