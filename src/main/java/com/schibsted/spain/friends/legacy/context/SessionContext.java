package com.schibsted.spain.friends.legacy.context;

import com.schibsted.spain.friends.legacy.model.User;

import java.util.Optional;

public interface SessionContext {

    void pushUser(User user);

    Optional<User> getUser(String username, String password);

    Optional<User> getUser(String username);
}
