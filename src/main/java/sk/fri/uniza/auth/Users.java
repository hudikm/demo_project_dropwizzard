package sk.fri.uniza.auth;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;


import java.util.*;

public class Users implements BasicDao<User, String> {
    final static Map<String, User> usersDB;

    static {
        User user = new User("martin", "martin", ImmutableSet.of(Roles.ADMIN));
        User user1 = new User("milan", "milan", ImmutableSet.of(Roles.READ_ONLY));
        User user2 = new User("matej", "matej", null);
        usersDB = new HashMap<>(ImmutableMap.of(
                user.getUserName(), user,
                user1.getUserName(), user1,
                user2.getUserName(), user2
        ));
    }

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(usersDB.get(id));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<User>(usersDB.values());
    }

    @Override
    public void save(User user) {
        usersDB.put(user.getUserName(), user);
    }

    @Override
    public void update(User user) {
        usersDB.put(user.getUserName(), user);
    }

    @Override
    public void delete(User user) {
        usersDB.put(user.getUserName(), user);
    }
}
