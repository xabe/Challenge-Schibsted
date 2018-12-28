package com.schibsted.spain.friends.legacy.context;

import com.schibsted.spain.friends.legacy.exception.FriendShipException;
import com.schibsted.spain.friends.legacy.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionContextImpl implements SessionContext {

    private final Map<String,User> userMap;

    public SessionContextImpl() {
        this.userMap = new ConcurrentHashMap();
    }

    @Override
    public void pushUser(User user) {
        if(userMap.containsKey(user.getUser())){
            throw new FriendShipException("User ");
        }
        userMap.putIfAbsent(user.getUser(), user);
    }

    @Override
    public Optional<User> getUser(String username, String password) {
        if(userMap.containsKey(username) && userMap.get(username).getPass().equals(password)){
            return Optional.of(userMap.get(username));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUser(String username) {
        return Optional.ofNullable(userMap.get(username));
    }
}
