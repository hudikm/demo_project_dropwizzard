package sk.fri.uniza.auth;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {
    final private String userName;
    final private  String password;
    final private Set<String> roles;

    public User(String userName, String password, Set<String> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return userName;
    }
}
