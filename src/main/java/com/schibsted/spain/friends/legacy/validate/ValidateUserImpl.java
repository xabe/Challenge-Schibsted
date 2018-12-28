package com.schibsted.spain.friends.legacy.validate;

import com.schibsted.spain.friends.legacy.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ValidateUserImpl implements ValidateUser {

    private static final String PATTERN_USERNAME = "([0-9A-Za-z]){5,10}";
    private static final String PATTERN_PASSWORD = "([0-9A-Za-z]){8,12}";

    private final Pattern patternUsername = Pattern.compile(PATTERN_USERNAME);
    private final Pattern patternPassword = Pattern.compile(PATTERN_PASSWORD);


    @Override
    public Optional<User> validateUser(String username, String password) {
        if(StringUtils.isNotBlank(username) &&
                StringUtils.isNotBlank(password) &&
                patternUsername.matcher(username).matches() &&
                patternPassword.matcher(password).matches()){
            final User user = new User(username, password);
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
