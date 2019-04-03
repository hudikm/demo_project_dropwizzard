package sk.fri.uniza.auth;

import com.google.common.collect.ImmutableSet;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public class ExampleAuthenticator implements Authenticator<BasicCredentials, User> {
    static Users users = new Users();

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Optional<User> user = users.get(credentials.getUsername());
        if (user.isPresent()) {
            if (user.get().getPassword().equals(credentials.getPassword())) {
                return user;
            }
        }

        return Optional.empty();
    }
}
