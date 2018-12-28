package com.schibsted.spain.friends.legacy.validate;

import com.schibsted.spain.friends.legacy.model.User;

import java.util.Optional;

public interface ValidateUser {

    Optional<User> validateUser(String username, String password);

}
